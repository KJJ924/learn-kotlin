package learn.kotlin.study.helloworld

/**
 * @author dkansk924@naver.com
 * @since 2021/07/16
 */


// 자바의 switch 와 비슷하지만 각 분기마다 break 를 선언할 필요는 없어짐
fun whenEx(card: Card): String {
    return when (card) {
        Card.SAMSUNG -> "삼성카드"
        Card.KAKAO -> "카카오카드"
    }
}

// 또한 여러개 매치 패턴을 지정할 수 도 있음 !
fun whenEx2(card: Card): String {
    return when (card) {
        Card.SAMSUNG, Card.KAKAO -> "그냥 카드 !"
    }
}

// switch 의 default 처럼 else 를 사용하여 분기 식에 만족하지 않는 경우를 설정할 수 있음
fun whenEx3(card: Card): String {
    return when (card) {
        Card.SAMSUNG -> "그냥 카드 !"
        else -> "KAKAO 이지만 .. 원하는 조건이 업서!"
    }
}

// 범위, 컬렉션에 포함되는지 체크도 가능
fun whenEx4(i: Int): String {
    return when (i) {
        in 1..10 -> "1~ 10 범위안에 포함됩니다."
        in 11..20 -> "11~ 20 범위안에 포함됩니다."
        else -> "설정한 경계값에 포함되지않습니다."
    }
}

fun main() {
    println(whenEx(Card.SAMSUNG))
    println(whenEx2(Card.KAKAO))
    println(whenEx3(Card.KAKAO))
    println(whenEx4(10))
}