/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.fir.test.cases.generated.cases.components.expressionTypeProvider;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.analysis.api.fir.test.configurators.AnalysisApiFirTestConfiguratorFactory;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiTestConfiguratorFactoryData;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiTestConfigurator;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.TestModuleKind;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.FrontendKind;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisSessionMode;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiMode;
import org.jetbrains.kotlin.analysis.api.impl.base.test.cases.components.expressionTypeProvider.AbstractExpectedExpressionTypeTest;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.analysis.api.GenerateAnalysisApiTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType")
@TestDataPath("$PROJECT_ROOT")
public class FirIdeNormalAnalysisSourceModuleExpectedExpressionTypeTestGenerated extends AbstractExpectedExpressionTypeTest {
    @NotNull
    @Override
    public AnalysisApiTestConfigurator getConfigurator() {
        return AnalysisApiFirTestConfiguratorFactory.INSTANCE.createConfigurator(
            new AnalysisApiTestConfiguratorFactoryData(
                FrontendKind.Fir,
                TestModuleKind.Source,
                AnalysisSessionMode.Normal,
                AnalysisApiMode.Ide
            )
        );
    }

    @Test
    @TestMetadata("afterExclOperand.kt")
    public void testAfterExclOperand() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/afterExclOperand.kt");
    }

    @Test
    public void testAllFilesPresentInExpectedExpressionType() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType"), Pattern.compile("^(.+)\\.kt$"), null, true);
    }

    @Test
    @TestMetadata("arrayAccessExpressionGet.kt")
    public void testArrayAccessExpressionGet() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/arrayAccessExpressionGet.kt");
    }

    @Test
    @TestMetadata("arrayAccessExpressionGetWithTypeParameters.kt")
    public void testArrayAccessExpressionGetWithTypeParameters() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/arrayAccessExpressionGetWithTypeParameters.kt");
    }

    @Test
    @TestMetadata("arrayAccessExpressionSet.kt")
    public void testArrayAccessExpressionSet() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/arrayAccessExpressionSet.kt");
    }

    @Test
    @TestMetadata("arrayAccessExpressionSetWithTypeParameters.kt")
    public void testArrayAccessExpressionSetWithTypeParameters() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/arrayAccessExpressionSetWithTypeParameters.kt");
    }

    @Test
    @TestMetadata("conditionInWhenWithSubject.kt")
    public void testConditionInWhenWithSubject() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/conditionInWhenWithSubject.kt");
    }

    @Test
    @TestMetadata("conditionInWhenWithoutSubject.kt")
    public void testConditionInWhenWithoutSubject() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/conditionInWhenWithoutSubject.kt");
    }

    @Test
    @TestMetadata("elvisExpressionLeftOperand.kt")
    public void testElvisExpressionLeftOperand() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/elvisExpressionLeftOperand.kt");
    }

    @Test
    @TestMetadata("elvisExpressionRightOperand.kt")
    public void testElvisExpressionRightOperand() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/elvisExpressionRightOperand.kt");
    }

    @Test
    @TestMetadata("functionExpressionBody.kt")
    public void testFunctionExpressionBody() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/functionExpressionBody.kt");
    }

    @Test
    @TestMetadata("functionExpressionBodyBlockExpression.kt")
    public void testFunctionExpressionBodyBlockExpression() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/functionExpressionBodyBlockExpression.kt");
    }

    @Test
    @TestMetadata("functionExpressionBodyQualified.kt")
    public void testFunctionExpressionBodyQualified() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/functionExpressionBodyQualified.kt");
    }

    @Test
    @TestMetadata("functionExpressionBodyWithTypeFromRHS.kt")
    public void testFunctionExpressionBodyWithTypeFromRHS() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/functionExpressionBodyWithTypeFromRHS.kt");
    }

    @Test
    @TestMetadata("functionExpressionBodyWithoutExplicitType.kt")
    public void testFunctionExpressionBodyWithoutExplicitType() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/functionExpressionBodyWithoutExplicitType.kt");
    }

    @Test
    @TestMetadata("functionLambdaParam.kt")
    public void testFunctionLambdaParam() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/functionLambdaParam.kt");
    }

    @Test
    @TestMetadata("functionNamedlParam.kt")
    public void testFunctionNamedlParam() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/functionNamedlParam.kt");
    }

    @Test
    @TestMetadata("functionParamWithTypeParam.kt")
    public void testFunctionParamWithTypeParam() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/functionParamWithTypeParam.kt");
    }

    @Test
    @TestMetadata("functionPositionalParam.kt")
    public void testFunctionPositionalParam() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/functionPositionalParam.kt");
    }

    @Test
    @TestMetadata("functionPositionalParamQualified.kt")
    public void testFunctionPositionalParamQualified() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/functionPositionalParamQualified.kt");
    }

    @Test
    @TestMetadata("ifCondition.kt")
    public void testIfCondition() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/ifCondition.kt");
    }

    @Test
    @TestMetadata("ifConditionQualified.kt")
    public void testIfConditionQualified() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/ifConditionQualified.kt");
    }

    @Test
    @TestMetadata("infixFunctionAsRegularCallParam.kt")
    public void testInfixFunctionAsRegularCallParam() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/infixFunctionAsRegularCallParam.kt");
    }

    @Test
    @TestMetadata("infixFunctionParam.kt")
    public void testInfixFunctionParam() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/infixFunctionParam.kt");
    }

    @Test
    @TestMetadata("infixFunctionParamQualified.kt")
    public void testInfixFunctionParamQualified() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/infixFunctionParamQualified.kt");
    }

    @Test
    @TestMetadata("infixFunctionTypeParameter.kt")
    public void testInfixFunctionTypeParameter() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/infixFunctionTypeParameter.kt");
    }

    @Test
    @TestMetadata("lambdaWithExplicitTypeFromVariable.kt")
    public void testLambdaWithExplicitTypeFromVariable() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/lambdaWithExplicitTypeFromVariable.kt");
    }

    @Test
    @TestMetadata("lambdaWithoutReturnNorExplicitType.kt")
    public void testLambdaWithoutReturnNorExplicitType() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/lambdaWithoutReturnNorExplicitType.kt");
    }

    @Test
    @TestMetadata("lastStatementInFunctionBlockBody.kt")
    public void testLastStatementInFunctionBlockBody() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/lastStatementInFunctionBlockBody.kt");
    }

    @Test
    @TestMetadata("lastStatementInLambda.kt")
    public void testLastStatementInLambda() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/lastStatementInLambda.kt");
    }

    @Test
    @TestMetadata("lastStatementInLambdaWithTypeMismatch.kt")
    public void testLastStatementInLambdaWithTypeMismatch() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/lastStatementInLambdaWithTypeMismatch.kt");
    }

    @Test
    @TestMetadata("lastStatementInLambdaWithoutExplicitType.kt")
    public void testLastStatementInLambdaWithoutExplicitType() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/lastStatementInLambdaWithoutExplicitType.kt");
    }

    @Test
    @TestMetadata("lastStatementInTry.kt")
    public void testLastStatementInTry() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/lastStatementInTry.kt");
    }

    @Test
    @TestMetadata("propertyDeclaration.kt")
    public void testPropertyDeclaration() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/propertyDeclaration.kt");
    }

    @Test
    @TestMetadata("propertyDeclarationQualified.kt")
    public void testPropertyDeclarationQualified() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/propertyDeclarationQualified.kt");
    }

    @Test
    @TestMetadata("propertyDeclarationWithSafeCast.kt")
    public void testPropertyDeclarationWithSafeCast() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/propertyDeclarationWithSafeCast.kt");
    }

    @Test
    @TestMetadata("propertyDeclarationWithTypeCast.kt")
    public void testPropertyDeclarationWithTypeCast() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/propertyDeclarationWithTypeCast.kt");
    }

    @Test
    @TestMetadata("propertyDeclarationWithTypeFromRHS.kt")
    public void testPropertyDeclarationWithTypeFromRHS() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/propertyDeclarationWithTypeFromRHS.kt");
    }

    @Test
    @TestMetadata("propertyDeclarationWithoutExplicitType.kt")
    public void testPropertyDeclarationWithoutExplicitType() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/propertyDeclarationWithoutExplicitType.kt");
    }

    @Test
    @TestMetadata("returnFromFunction.kt")
    public void testReturnFromFunction() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/returnFromFunction.kt");
    }

    @Test
    @TestMetadata("returnFromFunctionQualifiedReceiver.kt")
    public void testReturnFromFunctionQualifiedReceiver() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/returnFromFunctionQualifiedReceiver.kt");
    }

    @Test
    @TestMetadata("returnFromFunctionQualifiedSelector.kt")
    public void testReturnFromFunctionQualifiedSelector() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/returnFromFunctionQualifiedSelector.kt");
    }

    @Test
    @TestMetadata("returnFromLambda.kt")
    public void testReturnFromLambda() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/returnFromLambda.kt");
    }

    @Test
    @TestMetadata("sam.kt")
    public void testSam() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/sam.kt");
    }

    @Test
    @TestMetadata("samAsArgument.kt")
    public void testSamAsArgument() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/samAsArgument.kt");
    }

    @Test
    @TestMetadata("samAsConstructorArgument.kt")
    public void testSamAsConstructorArgument() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/samAsConstructorArgument.kt");
    }

    @Test
    @TestMetadata("samAsReturn.kt")
    public void testSamAsReturn() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/samAsReturn.kt");
    }

    @Test
    @TestMetadata("samReferenceAsArgument.kt")
    public void testSamReferenceAsArgument() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/samReferenceAsArgument.kt");
    }

    @Test
    @TestMetadata("samReferenceAsVararg.kt")
    public void testSamReferenceAsVararg() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/samReferenceAsVararg.kt");
    }

    @Test
    @TestMetadata("samReferenceWithTypeCast.kt")
    public void testSamReferenceWithTypeCast() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/samReferenceWithTypeCast.kt");
    }

    @Test
    @TestMetadata("samWithExplicitTypeFromProperty.kt")
    public void testSamWithExplicitTypeFromProperty() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/samWithExplicitTypeFromProperty.kt");
    }

    @Test
    @TestMetadata("samWithReturnToExplicitLabel.kt")
    public void testSamWithReturnToExplicitLabel() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/samWithReturnToExplicitLabel.kt");
    }

    @Test
    @TestMetadata("samWithReturnToImplicitLabel.kt")
    public void testSamWithReturnToImplicitLabel() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/samWithReturnToImplicitLabel.kt");
    }

    @Test
    @TestMetadata("samWithTypeCast.kt")
    public void testSamWithTypeCast() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/samWithTypeCast.kt");
    }

    @Test
    @TestMetadata("statementInIf.kt")
    public void testStatementInIf() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/statementInIf.kt");
    }

    @Test
    @TestMetadata("statementInIfBlockExpression.kt")
    public void testStatementInIfBlockExpression() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/statementInIfBlockExpression.kt");
    }

    @Test
    @TestMetadata("statementInWhen.kt")
    public void testStatementInWhen() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/statementInWhen.kt");
    }

    @Test
    @TestMetadata("statementInWhenBlockExpression.kt")
    public void testStatementInWhenBlockExpression() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/statementInWhenBlockExpression.kt");
    }

    @Test
    @TestMetadata("variableAssignment.kt")
    public void testVariableAssignment() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/variableAssignment.kt");
    }

    @Test
    @TestMetadata("variableAssignmentQualified.kt")
    public void testVariableAssignmentQualified() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/variableAssignmentQualified.kt");
    }

    @Test
    @TestMetadata("whileCondition.kt")
    public void testWhileCondition() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/whileCondition.kt");
    }

    @Test
    @TestMetadata("whileConditionQualified.kt")
    public void testWhileConditionQualified() throws Exception {
        runTest("analysis/analysis-api/testData/components/expressionTypeProvider/expectedExpressionType/whileConditionQualified.kt");
    }
}
