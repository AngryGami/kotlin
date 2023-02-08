/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.backend.jvm.lower

import org.jetbrains.kotlin.backend.common.FileLoweringPass
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.backend.common.phaser.makeIrFilePhase
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext
import org.jetbrains.kotlin.config.CommonConfigurationKeys
import org.jetbrains.kotlin.config.LanguageVersion
import org.jetbrains.kotlin.descriptors.ClassKind
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrDeclaration
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol
import org.jetbrains.kotlin.ir.types.classifierOrNull
import org.jetbrains.kotlin.ir.types.getClass
import org.jetbrains.kotlin.ir.types.superTypes
import org.jetbrains.kotlin.ir.util.isFromJava
import org.jetbrains.kotlin.ir.util.parentClassOrNull
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid

internal val addSuperQualifierToJavaFieldAccessPhase = makeIrFilePhase<JvmBackendContext>(
    { context ->
        if (context.state.configuration.getBoolean(CommonConfigurationKeys.USE_FIR)) {
            AddSuperQualifierToJavaFieldAccessLowering(context)
        } else {
            FileLoweringPass.Empty
        }
    },
    name = "AddSuperQualifierToJavaFieldAccess",
    description = "Make `\$delegate` methods for optimized delegated properties static",
)

private class AddSuperQualifierToJavaFieldAccessLowering(val context: JvmBackendContext) : IrElementTransformerVoid(), FileLoweringPass {
    override fun lower(irFile: IrFile) {
        irFile.transform(this, null)
    }

    override fun visitFieldAccess(expression: IrFieldAccessExpression): IrExpression {
        val dispatchReceiver = expression.receiver
        if (dispatchReceiver != null) {
            expression.superQualifierSymbol = superQualifierSymbolForField(dispatchReceiver, expression.symbol)
        }
        return super.visitFieldAccess(expression)
    }

    // Note: I don't like using super qualifier symbols to determine field receivers in bytecode properly.
    // Would be much better to use super qualifiers only in case when it's used explicitly.
    // However, FE 1.0 does it, and currently we have no better way to provide these receivers.
    // See KT-49507 and KT-48954 as good examples for cases we try to handle here
    private fun superQualifierSymbolForField(dispatchReceiver: IrExpression, fieldSymbol: IrFieldSymbol): IrClassSymbol? {
        if (fieldSymbol.owner.correspondingPropertySymbol != null) return null
        val originalContainingClass = fieldSymbol.owner.parentClassOrNull ?: return null
        val dispatchReceiverIrType = dispatchReceiver.type
        val dispatchReceiverRepresentativeClassifierSymbol = dispatchReceiverIrType.classifierOrNull ?: return null
        // Find first Java super class to avoid possible visibility exposure & separate compilation problems
        return getJavaFieldContainingClassSymbol(dispatchReceiverRepresentativeClassifierSymbol, originalContainingClass.symbol)
    }

    // Note: ownContainingClass here is the use-site receiver class,
    // and originalContainingClass is the class which contains Java field we are trying to access
    // ! Interfaces are out of our interests here !
    // This function returns a class symbol which:
    // - is the most derived Java class in hierarchy which has no Kotlin base classes (including transitive ones)
    // E.g. K2 <: J3 <: K1 <: J2 <: J1 ==> J2 is chosen
    // We shouldn't allow base Kotlin classes to avoid possible clashes with invisible properties inside
    private fun getJavaFieldContainingClassSymbol(
        dispatchReceiverRepresentativeClassifierSymbol: IrClassifierSymbol,
        originalContainingClassSymbol: IrClassSymbol
    ): IrClassSymbol {
        var superQualifierClassifierSymbol = dispatchReceiverRepresentativeClassifierSymbol
        var superQualifierClassFromJava: IrClass? = dispatchReceiverRepresentativeClassifierSymbol.ownerClassIfFromJava()
        while (superQualifierClassifierSymbol !== originalContainingClassSymbol) {
            superQualifierClassifierSymbol = superQualifierClassifierSymbol.superTypes().find {
                val kind = it.getClass()?.kind
                // Note: for class we will find class here,
                // for type parameter either class or another type parameter (they cannot be in supertypes together)
                kind == ClassKind.CLASS || kind == ClassKind.ENUM_CLASS || kind == null
            }?.classifierOrNull ?: break
            val isFromJava = superQualifierClassifierSymbol.isFromJava()
            if (superQualifierClassFromJava == null) {
                superQualifierClassFromJava = superQualifierClassifierSymbol.ownerClassIfFromJava()
            } else if (!isFromJava) {
                superQualifierClassFromJava = null
            }
        }
        return superQualifierClassFromJava?.symbol ?: originalContainingClassSymbol
    }

    private fun IrClassifierSymbol.isFromJava() = (owner as? IrDeclaration)?.isFromJava() == true

    private fun IrClassifierSymbol.ownerClassIfFromJava(): IrClass? = (owner as? IrClass)?.takeIf { it.isFromJava() }
}
