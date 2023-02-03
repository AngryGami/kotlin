/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.low.level.api.fir.file.builder

import org.jetbrains.kotlin.analysis.api.impl.barebone.annotations.ThreadSafe
import org.jetbrains.kotlin.analysis.low.level.api.fir.LLFirModuleResolveComponents
import org.jetbrains.kotlin.analysis.low.level.api.fir.util.LLFirExceptionHandler
import org.jetbrains.kotlin.fir.builder.RawFirBuilder
import org.jetbrains.kotlin.fir.builder.BodyBuildingMode
import org.jetbrains.kotlin.fir.declarations.FirDeclarationDataKey
import org.jetbrains.kotlin.fir.declarations.FirDeclarationDataRegistry
import org.jetbrains.kotlin.fir.declarations.FirFile
import org.jetbrains.kotlin.psi.KtFile

private object FileStructureStampKey : FirDeclarationDataKey()

internal var FirFile.fileStructureStamp: Long? by FirDeclarationDataRegistry.data(FileStructureStampKey)

/**
 * Responsible for building [FirFile] by [KtFile]
 */
@ThreadSafe
internal class LLFirFileBuilder(
    val moduleComponents: LLFirModuleResolveComponents,
) {
    fun buildRawFirFileWithCaching(ktFile: KtFile): FirFile = moduleComponents.cache.fileCached(ktFile) {
        val bodyBuildingMode = when {
            ktFile.isScript() -> {
                // As 'FirScript' content is never transformed, lazy bodies are not replaced with calculated ones even on BODY_RESOLVE.
                // Such behavior breaks file structure mapping computation.
                // TODO: remove this clause when proper support for scripts is implemented in K2.
                BodyBuildingMode.NORMAL
            }
            else -> BodyBuildingMode.LAZY_BODIES
        }

        val builder = RawFirBuilder(moduleComponents.session, moduleComponents.scopeProvider, bodyBuildingMode = bodyBuildingMode)

        builder.buildFirFile(ktFile).apply {
            fileStructureStamp = LLFirExceptionHandler.modificationCount
        }
    }
}


