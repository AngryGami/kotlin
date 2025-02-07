/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.plugin.mpp

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginLifecycle
import org.jetbrains.kotlin.gradle.plugin.launchInStage
import org.jetbrains.kotlin.gradle.plugin.sources.android.androidSourceSetInfoOrNull
import org.jetbrains.kotlin.gradle.plugin.sources.internal

object UnusedSourceSetsChecker {
    const val WARNING_PREFIX_ONE =
        "The Kotlin source set"

    const val WARNING_PREFIX_MANY =
        "The following Kotlin source sets were"

    const val WARNING_INTRO = "configured but not added to any Kotlin compilation"

    const val WARNING_BOTTOM_LINE =
        "You can add a source set to a target's compilation by connecting it with the compilation's default source set using 'dependsOn'.\n" +
                "See https://kotl.in/connecting-source-sets"

    private fun reportUnusedSourceSets(project: Project, sourceSets: Set<KotlinSourceSet>) {
        require(sourceSets.isNotEmpty())
        val message = when (sourceSets.size) {
            1 -> "$WARNING_PREFIX_ONE ${sourceSets.single().name} was $WARNING_INTRO. $WARNING_BOTTOM_LINE"
            else -> {
                val list = sourceSets.joinToString("\n", "\n", "\n") { " * ${it.name}" }
                "$WARNING_PREFIX_MANY $WARNING_INTRO:$list$WARNING_BOTTOM_LINE"
            }
        }
        project.logger.warn("\n" + message) // make sure the message stands out
    }

    fun checkSourceSets(project: Project) {
        project.launchInStage(KotlinPluginLifecycle.Stage.ReadyForExecution) {
            val unusedSourceSets = project.kotlinExtension.sourceSets
                // Ignoring Android source sets
                .filter { it.androidSourceSetInfoOrNull == null }
                .filter { it.internal.compilations.isEmpty() }
            if (unusedSourceSets.isNotEmpty()) {
                reportUnusedSourceSets(project, unusedSourceSets.toSet())
            }
        }
    }
}
