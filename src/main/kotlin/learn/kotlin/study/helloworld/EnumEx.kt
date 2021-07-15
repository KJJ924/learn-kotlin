package learn.kotlin.study.helloworld

import java.util.function.Function

/**
 * @author dkansk924@naver.com
 * @since 2021/07/15
 */

enum class Card(val cardName: String, private val expression: Function<Int, Int>) {
    KAKAO("카카오페이", { i -> i / 100 }),
    SAMSUNG("삼성페이", { i -> i * 2 / 100 });

    fun payBack(money: Int) = expression.apply(money)

}

fun main() {
    println("${Card.KAKAO.cardName} =  ${Card.KAKAO.payBack(1000)}")
    println("${Card.SAMSUNG.cardName} = ${Card.SAMSUNG.payBack(1000)}")
}