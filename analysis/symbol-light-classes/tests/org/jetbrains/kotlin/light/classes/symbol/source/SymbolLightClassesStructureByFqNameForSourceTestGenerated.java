/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.light.classes.symbol.source;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.analysis.api.GenerateAnalysisApiTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("analysis/symbol-light-classes/testData/structureByFqName")
@TestDataPath("$PROJECT_ROOT")
public class SymbolLightClassesStructureByFqNameForSourceTestGenerated extends AbstractSymbolLightClassesStructureByFqNameForSourceTest {
    @Test
    public void testAllFilesPresentInStructureByFqName() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/symbol-light-classes/testData/structureByFqName"), Pattern.compile("^(.+)\\.(kt)$"), null, true);
    }

    @Test
    @TestMetadata("DefaultImpls.kt")
    public void testDefaultImpls() throws Exception {
        runTest("analysis/symbol-light-classes/testData/structureByFqName/DefaultImpls.kt");
    }
}
