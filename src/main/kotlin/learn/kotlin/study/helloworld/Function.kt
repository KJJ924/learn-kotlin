package learn.kotlin.study.helloworld

/**
 * @author dkansk924@naver.com
 * @since 2021/07/15
 */

fun printHello(message: String) { // return 값이 없는 경우
    println("Hello $message !")
}


fun sum(a: Int, b: Int): Int { // return 값이  있는경우  :Type 반환 타입 지정
    return a + b
}

fun sum2(a: Int, b: Int): Int = a + b //람다와 비슷하게 할 수 있음
fun sum3(a: Int, b: Int) = a + b // 또한 타입 추론을 이용하여 리턴타입 생략할 수 있음

fun main() {
    printHello("Kotlin")

    println("result= ${sum(10, 20)}") // 이런것도 됨!!!!
    val result = sum3(b = 10, a = 20) // 파라미터의 순서와 상관없이 원하는 값을 지정하여 전달 할 수 있음
    println(result)
}
