/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.low.level.api.fir;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.analysis.api.GenerateAnalysisApiTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("analysis/low-level-api-fir/testdata/fileBasedDeclarationProvider")
@TestDataPath("$PROJECT_ROOT")
public class FileBasedKotlinDeclarationProviderTestGenerated extends AbstractFileBasedKotlinDeclarationProviderTest {
    @Test
    public void testAllFilesPresentInFileBasedDeclarationProvider() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/low-level-api-fir/testdata/fileBasedDeclarationProvider"), Pattern.compile("^(.+)\\.(kt|kts)$"), null, true);
    }

    @Test
    @TestMetadata("defaultPackage.kt")
    public void testDefaultPackage() throws Exception {
        runTest("analysis/low-level-api-fir/testdata/fileBasedDeclarationProvider/defaultPackage.kt");
    }

    @Test
    @TestMetadata("local.kt")
    public void testLocal() throws Exception {
        runTest("analysis/low-level-api-fir/testdata/fileBasedDeclarationProvider/local.kt");
    }

    @Test
    @TestMetadata("nestedTypeAlias.kt")
    public void testNestedTypeAlias() throws Exception {
        runTest("analysis/low-level-api-fir/testdata/fileBasedDeclarationProvider/nestedTypeAlias.kt");
    }

    @Test
    @TestMetadata("sameNames.kt")
    public void testSameNames() throws Exception {
        runTest("analysis/low-level-api-fir/testdata/fileBasedDeclarationProvider/sameNames.kt");
    }

    @Test
    @TestMetadata("script.kts")
    public void testScript() throws Exception {
        runTest("analysis/low-level-api-fir/testdata/fileBasedDeclarationProvider/script.kts");
    }

    @Test
    @TestMetadata("simple.kt")
    public void testSimple() throws Exception {
        runTest("analysis/low-level-api-fir/testdata/fileBasedDeclarationProvider/simple.kt");
    }
}
