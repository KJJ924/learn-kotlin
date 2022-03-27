package learn.kotlin.study.effective.item21


import kotlin.properties.Delegates
import kotlin.reflect.KProperty


val value: String by lazy { "init" }


var key: String? by Delegates.observable(null) { _, oldValue, newValue ->
    println("key changed from $oldValue to $newValue")
}

fun main() {
    val foo = Foo()
    println(foo.value)
}


class Foo {
    val value by LoggingProperty(null)
}

private class LoggingProperty<T>(var value: T) {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): T {
        println("${prop.name} get value $value")
        return value
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, newValue: T) {
        println("${prop.name} set value ${this.value} -> $newValue")
        this.value = newValue
    }
}