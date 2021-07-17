package learn.kotlin.study.helloworld

/**
 * @author dkansk924@naver.com
 * @since 2021/07/17
 */


// 확장함수
// 확장함수 안에서 수신 객체의 메서드나 프로퍼티 사용가능 (public 맴버에만 접근가능)
// 확장함수는 실제로는 정적메서드를 호출하는 것이다. public static method
fun Collection<String>.hello(
    prefix: String = "hello",
    suffix: String = "!") {
    val iterator = this.iterator()  // 여기서 this 수신객체(Collection)
    while (iterator.hasNext()) {
        println("$prefix ${iterator.next()} $suffix")
    }
}

fun main() {
    listOf("kjj","jaejoon").hello("안녕","!")
}