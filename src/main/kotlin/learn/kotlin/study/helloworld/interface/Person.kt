package learn.kotlin.study.helloworld.`interface`

/**
 * @author dkansk924@naver.com
 * @since 2021/07/17
 */
// 인터페이스의 정의는 기본 Java 와 크게 다른 것 없다.
// 하지만 java 의 인터페이스에선 상수가 정의가능하지만 코틀린에서는 불가능하다.
interface Person {
    // val value ="Stirng"  정의 불가능
    fun language()

    //default 메서드 구현시 메서드 본문을 시그니처 뒤에 추가하면된다.
    fun walk(){
        println("걷다")
    }
}