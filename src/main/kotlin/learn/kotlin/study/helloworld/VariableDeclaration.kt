package learn.kotlin.study.helloworld

/**
 * @author dkansk924@naver.com
 * @since 2021/07/15
 */

fun main() {
    // val 선언할때만 초기화 가능 final 추후 변경 불가능
    val name = "JAE JOON"

    // var 언제든지 읽기 쓰기가 가능함
    var a = 10
    var b: Int = 10 // 타입 추론이 아닌 타입을 지정 해준다.(생략 가능)
    var age: Int // 초기화 값이 없다면 반드시 타입을 지정해야한다.
    var status: Boolean
}
