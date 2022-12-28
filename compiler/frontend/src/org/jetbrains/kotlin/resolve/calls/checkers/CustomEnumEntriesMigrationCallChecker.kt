/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.resolve.calls.checkers

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.builtins.StandardNames
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.diagnostics.Errors
import org.jetbrains.kotlin.resolve.DescriptorUtils
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
import org.jetbrains.kotlin.resolve.descriptorUtil.isCompanionObject
import org.jetbrains.kotlin.resolve.scopes.receivers.ClassValueReceiver
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue

object CustomEnumEntriesMigrationCallChecker : CallChecker {
    override fun check(resolvedCall: ResolvedCall<*>, reportOn: PsiElement, context: CallCheckerContext) {
        val descriptor = resolvedCall.resultingDescriptor
        if (descriptor !is PropertyDescriptor) return
        if (descriptor.name != StandardNames.ENUM_ENTRIES) return

        if (resolvedCall.isExtensionWithEnumClassQualifier() ||
            resolvedCall.isCallViaCompanionOnEnumClassQualifier(descriptor)
        ) {
            context.trace.report(Errors.DEPRECATED_ACCESS_TO_ENUM_ENTRY_COMPANION_PROPERTY.on(reportOn))
        }
    }

    private fun ResolvedCall<*>.isExtensionWithEnumClassQualifier(): Boolean {
        val receiver = extensionReceiver ?: return false
        return receiver is ClassValueReceiver && DescriptorUtils.isEnumClass(receiver.classQualifier.descriptor)
    }

    private fun ResolvedCall<*>.isCallViaCompanionOnEnumClassQualifier(descriptor: PropertyDescriptor): Boolean {
        val containingDeclaration = descriptor.containingDeclaration
        if (!containingDeclaration.isCompanionObject()) return false

        val grandParent = containingDeclaration.containingDeclaration ?: return false
        if (grandParent !is ClassDescriptor || !DescriptorUtils.isEnumClass(grandParent)) return false

        return dispatchReceiver.isQualifierFor(grandParent)
    }
}