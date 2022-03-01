package learn.kotlin.study.effective.item7

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class Person(var name: String, var age: Int)


inline fun <reified T : Any> String.isParsing(): Result<T> {
    if(this.isEmpty()){
        return Failure(IllegalArgumentException()) // ex
    }
    val jacksonObjectMapper = jacksonObjectMapper()
    return Success(jacksonObjectMapper.readValue(this, T::class.java))
}


fun main() {
    val json = "{\"name\":\"jaejoon\",\"age\":\"99\"}"
    val person = json.isParsing<Person>();
    val emptyPerson =  "".isParsing<Person>()

    val age = when(emptyPerson) {
        is Success -> emptyPerson.result.age
        is Failure -> -1
    }

    println(age)
}