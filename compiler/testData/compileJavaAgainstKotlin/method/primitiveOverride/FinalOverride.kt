package test

trait A<T> {
    fun foo(): T
}

open class B : A<Int> {
    override final fun foo(): Int = 42
}
