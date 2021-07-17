package learn.kotlin.study.helloworld.`interface`

/**
 * @author dkansk924@naver.com
 * @since 2021/07/17
 */

// Java 와 똑같이 다중상속은 불가능하고 , 인터페이스는 다중 구현이 가능하다.
class JaeJoon : Person{

    // 기존 java 의 @Override 애노테이션은 선택이였지만 코틀린에서는 반드시 재정의시 꼭 사용해야한다.
    override fun language() {
        println("한국어 사용")
    }
}

fun main() {
    val jaejoon = JaeJoon()
    jaejoon.language()
    jaejoon.walk()
}