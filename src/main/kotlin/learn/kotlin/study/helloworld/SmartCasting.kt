package learn.kotlin.study.helloworld


/**
 * @author dkansk924@naver.com
 * @since 2021/07/16
 */
fun smartCasting(i: Any): Any {
    return when (i) {
        is Number -> i // is 로 타입검사 후 컴파일러가 타입 캐스팅 해줌
        // is Number -> i as Number  다음과 같이 프로그래머가 캐스팅을 직접해줄 필요없음! if 문에서도 똑같음
        else -> throw ClassCastException("casting error")
    }
}

fun main() {
    smartCasting(1)
}
