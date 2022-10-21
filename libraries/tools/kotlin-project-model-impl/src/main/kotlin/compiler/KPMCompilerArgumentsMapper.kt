/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.project.modelx.compiler

import compiler.Config
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments
import org.jetbrains.kotlin.cli.common.arguments.K2JSCompilerArguments
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments
import org.jetbrains.kotlin.cli.common.arguments.K2MetadataCompilerArguments
import org.jetbrains.kotlin.project.modelx.*
import org.jetbrains.kotlin.project.modelx.languageSetting.AnyLanguageSettingValue

/**
 * Converts [FragmentCompilerSettings], [VariantCompilerSettings] to compiler-native arguments:
 *  * [K2MetadataCompilerArguments]
 *  * [K2JVMCompilerArguments]
 *  * [K2JSCompilerArguments]
 *  * etc...
 */
class KPMCompilerArgumentsMapper(
    private val adapter: KpmBuildSystemAdapter,
    private val config: Config
) {
    private val kpmFileStructure = config.kpmFileStructure

    fun metadataArguments(compilation: FragmentCompilerSettings): K2MetadataCompilerArguments {
        val args = K2MetadataCompilerArguments()
        args.multiPlatform = true
        args.expectActualLinker = true

        args.destination = adapter.fragmentMetadataClasspath(compilation.fragment.id)

        val refinementsPaths = compilation
            .refinements
            .map(adapter::fragmentMetadataClasspath)

        val classpath = compilation
            .dependencies
            .flatMap(adapter::fragmentDependencyMetadataArtifacts)
            .plus(refinementsPaths)
            .distinct()
            .joinToString(":")
        args.classpath = listOfNotNull(args.classpath, classpath).joinToString(":")

        args.friendPaths = refinementsPaths.toTypedArray()

        args.freeArgs += adapter.fragmentSources(compilation.fragment.id)

        return args
    }

    fun arguments(compilation: VariantCompilerSettings): CommonCompilerArguments {
        val args = when (compilation.variant.platform) {
            Platform.JVM -> K2JVMCompilerArguments().apply { defaultJvmArgs() }
            Platform.JS -> K2JSCompilerArguments().apply { defaultJsArgs() }
            Platform.Native -> TODO("Native is not supported yet")
        }

        args.apply {
            addAttributes(compilation.variant.attributes)
            addSettings(compilation.variant.settings)
            addDependencies(compilation.dependencies)
            addSources(compilation.variant.id, compilation.refinements)
            specifyDestination(compilation.variant.id)
        }

        return args
    }

    private fun K2JVMCompilerArguments.defaultJvmArgs() {
        noStdlib = true
        noReflect = true
    }

    private fun K2JSCompilerArguments.defaultJsArgs() {
        noStdlib = true

        metaInfo = true
    }

    private fun CommonCompilerArguments.addAttributes(attributes: Map<Attribute.Key, Attribute>) {
        val mapper = config.attributeContributors[javaClass] ?: error("No Attribute mapper found for $javaClass")
        attributes.forEach { mapper(it.key to it.value) }
    }

    private fun CommonCompilerArguments.addSettings(settings: Map<String, AnyLanguageSettingValue>) {
        val mapper = config.settingsContributors[javaClass] ?: error("No Settings mapper found for $javaClass")
        settings.forEach { mapper(it.key to it.value) }
    }

    private fun CommonCompilerArguments.addDependencies(fragmentDependencies: Set<FragmentDependency>) {
        val dependencies = fragmentDependencies
            .flatMap(adapter::variantDependencyArtifacts)
            .distinct()
            .joinToString(":")

        when (this) {
            is K2JVMCompilerArguments -> classpath = dependencies
            is K2JSCompilerArguments -> libraries = dependencies
            else -> error("Unsupported arguments $this")
        }
    }

    private fun CommonCompilerArguments.addSources(mainFragment: FragmentId, commonFragments: Set<FragmentId>) {
        multiPlatform = true
        freeArgs = freeArgs + adapter.fragmentSources(mainFragment) + commonFragments.flatMap(adapter::fragmentSources)
    }

    private fun CommonCompilerArguments.specifyDestination(variantId: FragmentId) {
        when (this) {
            is K2JVMCompilerArguments -> destination = kpmFileStructure.jvmOutputDir(variantId).toString()
            is K2JSCompilerArguments -> outputFile = kpmFileStructure
                .jsOutputDir(variantId)
                .resolve("${adapter.module.id}.js")
                .toString()
            else -> error("Unsupported arguments $this")
        }
    }
}