/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.fir.scopes

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.declarations.*
import org.jetbrains.kotlin.fir.declarations.utils.classId
import org.jetbrains.kotlin.fir.declarations.utils.isSynthetic
import org.jetbrains.kotlin.fir.java.declarations.FirJavaClass
import org.jetbrains.kotlin.fir.java.enhancement.FirSignatureEnhancement
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
import org.jetbrains.kotlin.fir.scopes.FirTypeScope
import org.jetbrains.kotlin.fir.scopes.getDirectOverriddenMembers
import org.jetbrains.kotlin.fir.scopes.getDirectOverriddenProperties
import org.jetbrains.kotlin.fir.scopes.impl.nestedClassifierScope
import org.jetbrains.kotlin.fir.symbols.impl.*
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.name.SpecialNames
import org.jetbrains.kotlin.util.collectionUtils.filterIsInstanceAnd

internal class JavaClassDeclaredMembersEnhancementScope(
    private val useSiteSession: FirSession,
    private val owner: FirJavaClass,
    private val useSiteMemberEnhancementScope: FirTypeScope
) : FirContainingNamesAwareScope() {
    private val signatureEnhancement = FirSignatureEnhancement(owner, useSiteSession) {
        overriddenMembers()
    }

    private val callablesIndex = run {
        val result = mutableMapOf<Name, MutableList<FirCallableSymbol<*>>>()
        owner.declarations.filterIsInstanceAnd<FirCallableDeclaration> {
            it is FirConstructor ||
                    (it.dispatchReceiverType as? ConeLookupTagBasedType)?.lookupTag == owner.symbol.toLookupTag()
                    && it.origin != FirDeclarationOrigin.SubstitutionOverride
                    && it.origin != FirDeclarationOrigin.IntersectionOverride
        }.forEach { declaration ->
            val name = when (declaration) {
                is FirConstructor -> SpecialNames.INIT
                is FirVariable -> if (declaration.isSynthetic) null else declaration.name
                is FirSimpleFunction -> declaration.name
                else -> null
            }
            if (name != null) {
                val enhancement = when (declaration) {
                    is FirFunction -> {
                        val symbol = signatureEnhancement.enhancedFunction(declaration.symbol, name)
                        val enhancedFunction = (symbol.fir as? FirSimpleFunction)
                        enhancedFunction?.symbol ?: symbol
                    }
                    is FirVariable -> {
                        signatureEnhancement.enhancedProperty(declaration.symbol, name)
                    }
                    else -> declaration.symbol
                }
                result.getOrPut(name) { mutableListOf() } += enhancement
            }
        }
        result
    }

    private val nestedClassifierScope: FirContainingNamesAwareScope? =
        useSiteSession.nestedClassifierScope(owner)

    override fun getCallableNames(): Set<Name> {
        return callablesIndex.keys
    }

    override fun getClassifierNames(): Set<Name> {
        return nestedClassifierScope?.getClassifierNames().orEmpty()
    }

    override fun processFunctionsByName(name: Name, processor: (FirNamedFunctionSymbol) -> Unit) {
        if (name == SpecialNames.INIT) return
        useSiteMemberEnhancementScope.processFunctionsByName(name) { symbol ->
            if (callablesIndex[name]?.contains(symbol) == true) {
                processor(symbol)
            }
        }
    }

    override fun processClassifiersByNameWithSubstitution(
        name: Name,
        processor: (FirClassifierSymbol<*>, ConeSubstitutor) -> Unit
    ) {
        nestedClassifierScope?.processClassifiersByNameWithSubstitution(name, processor)
    }

    override fun processDeclaredConstructors(processor: (FirConstructorSymbol) -> Unit) {
        useSiteMemberEnhancementScope.processDeclaredConstructors { symbol ->
            if (callablesIndex[SpecialNames.INIT]?.contains(symbol) == true) {
                processor(symbol)
            }
        }
    }

    override fun processPropertiesByName(name: Name, processor: (FirVariableSymbol<*>) -> Unit) {
        if (name !in callablesIndex) return
        useSiteMemberEnhancementScope.processPropertiesByName(name) { original ->
            if (callablesIndex[name]?.contains(original) == true) {
                processor(original)
            }
        }
    }

    override fun toString(): String {
        return "Java enhancement declared member scope for ${owner.classId}"
    }

    private fun FirCallableDeclaration.overriddenMembers(): List<FirCallableDeclaration> {
        return when (val symbol = this.symbol) {
            is FirNamedFunctionSymbol -> useSiteMemberEnhancementScope.getDirectOverriddenMembers(symbol)
            is FirPropertySymbol -> useSiteMemberEnhancementScope.getDirectOverriddenProperties(symbol)
            else -> emptyList()
        }.map { it.fir }
    }
}