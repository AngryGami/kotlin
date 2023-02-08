import abitestutils.abiTest

fun box() = abiTest {
    val ii: InterfaceImpl = InterfaceImpl()
    val i: Interface = ii
    val aci: AbstractClassImpl = AbstractClassImpl()
    val ac: AbstractClass = aci
    val oci: OpenClassImpl = OpenClassImpl()
    val oc: OpenClass = oci

    expectSuccess("memberOperatorsToNonOperators: a=Alice,b=Bob") { memberOperatorsToNonOperators("a" to "Alice", "b" to "Bob") }
    expectSuccess("extensionOperatorsToNonOperators: a=Alice,b=Bob") { extensionOperatorsToNonOperators("a" to "Alice", "b" to "Bob") }
    expectSuccess("memberNonOperatorsToOperators: a=Alice,b=Bob") { memberNonOperatorsToOperators("a" to "Alice", "b" to "Bob") }
    expectSuccess("extensionNonOperatorsToOperators: a=Alice,b=Bob") { extensionNonOperatorsToOperators("a" to "Alice", "b" to "Bob") }

    expectSuccess(3) { memberNonInfixToInfix(1, 2) }
    expectSuccess(3) { extensionNonInfixToInfix(1, 2) }
    expectSuccess(3) { memberInfixToNonInfix(1, 2) }
    expectSuccess(3) { extensionInfixToNonInfix(1, 2) }

    expectSuccess(6) { nonTailrecToTailrec(3) }
    expectSuccess(6) { tailrecToNonTailrec(3) }

    expectFailure(linkage("Function 'removedDefaultValue' can not be called: The call site provides less value arguments (1) then the function requires (2)")) { removedDefaultValueInFunction(1) }
    expectFailure(linkage("Constructor 'RemovedDefaultValueInConstructor.<init>' can not be called: The call site provides less value arguments (1) then the constructor requires (2)")) { removedDefaultValueInConstructor(1) }

    expectSuccess(-1) { suspendToNonSuspendFunction1(1) }
    expectSuccess(-2) { suspendToNonSuspendFunction2(2) }
    expectSuccess(-3) { suspendToNonSuspendFunction3(3) }
    expectFailure(linkage("Suspend expression can be called only from a coroutine or another suspend function")) { nonSuspendToSuspendFunction1(4) }
    expectSuccess(-5) { nonSuspendToSuspendFunction2(5) }
    expectFailure(linkage("Suspend expression can be called only from a coroutine or another suspend function")) { nonSuspendToSuspendFunction3(6) }
    expectSuccess(-7) { nonSuspendToSuspendFunction4(7) }

    expectFailure(linkage("?")) { suspendToNonSuspendFunctionInInterface(i, 1) }
    expectFailure(linkage("Suspend expression can be called only from a coroutine or another suspend function")) { nonSuspendToSuspendFunctionInInterface(i, 2) }
    expectSuccess(-3) { suspendToNonSuspendFunctionInInterfaceImpl(ii, 3) }
    expectSuccess(-4) { nonSuspendToSuspendFunctionInInterfaceImpl(ii, 4) }
    expectFailure(linkage("?")) { suspendToNonSuspendFunctionInAbstractClass(ac, 5) }
    expectFailure(linkage("Suspend expression can be called only from a coroutine or another suspend function")) { nonSuspendToSuspendFunctionInAbstractClass(ac, 6) }
    expectSuccess(-7) { suspendToNonSuspendFunctionInAbstractClassImpl(aci, 7) }
    expectSuccess(-8) { nonSuspendToSuspendFunctionInAbstractClassImpl(aci, 8) }
    expectFailure(linkage("?")) { suspendToNonSuspendFunctionInOpenClass(oc, 9) }
    expectFailure(linkage("Suspend expression can be called only from a coroutine or another suspend function")) { nonSuspendToSuspendFunctionInOpenClass(oc, 10) }
    expectSuccess(-11) { suspendToNonSuspendFunctionInOpenClassImpl(oci, 11) }
    expectSuccess(-12) { nonSuspendToSuspendFunctionInOpenClassImpl(oci, 12) }
    expectSuccess(-26) { suspendToNonSuspendFunctionWithDelegation(oci, 13) }
    expectFailure(linkage("Suspend expression can be called only from a coroutine or another suspend function")) { nonSuspendToSuspendFunctionWithDelegation(oci, 14) }
}
