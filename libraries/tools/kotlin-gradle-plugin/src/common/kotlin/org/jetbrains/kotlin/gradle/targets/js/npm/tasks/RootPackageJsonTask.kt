/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.targets.js.npm.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import org.gradle.work.NormalizeLineEndings
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin.Companion.kotlinNodeJsExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin.Companion.kotlinNpmResolutionManager
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProject
import org.jetbrains.kotlin.gradle.targets.js.npm.asNpmEnvironment
import org.jetbrains.kotlin.gradle.targets.js.npm.asYarnEnvironment
import org.jetbrains.kotlin.gradle.targets.js.yarn.yarn
import java.io.File

open class RootPackageJsonTask : DefaultTask() {
    init {
        check(project == project.rootProject)

        onlyIf {
            resolutionManager.get().isConfiguringState()
        }
    }

    // Only in configuration phase
    // Not part of configuration caching

    @Transient
    private val nodeJs = project.rootProject.kotlinNodeJsExtension

    @Transient
    private val yarn = project.rootProject.yarn

    // -----

    private val resolutionManager = project.rootProject.kotlinNpmResolutionManager

    private val npmEnvironment by lazy {
        nodeJs.requireConfigured().asNpmEnvironment
    }

    private val yarnEnv by lazy {
        yarn.requireConfigured().asYarnEnvironment
    }

    @get:OutputFile
    val rootPackageJson: File by lazy {
        nodeJs.rootPackageDir.resolve(NpmProject.PACKAGE_JSON)
    }

//    @get:PathSensitive(PathSensitivity.RELATIVE)
//    @get:IgnoreEmptyDirectories
//    @get:NormalizeLineEndings
//    @get:InputFiles
//    val packageJsonFiles: Collection<File> by lazy {
//        resolutionManager.get().packageJsonFiles
//    }

    @TaskAction
    fun resolve() {
        resolutionManager.get().prepare(logger, npmEnvironment, yarnEnv)
    }

    companion object {
        const val NAME = "rootPackageJson"
    }
}