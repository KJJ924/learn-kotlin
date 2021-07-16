package learn.kotlin.study.helloworld

/**
 * @author dkansk924@naver.com
 * @since 2021/07/16
 */

fun main() {
    // 자바의 HashMap 생성 기능은 동일
    val map = hashMapOf<String, Int>()
    map["hello"] = 1 // 이렇게도 값을 할당할 수 있다. map.put() 과 동일
    val i = map["hello"] // 이런식으로도 값을 가져올수 있음 map.get() 과 동일

    // 자바의 ArrayList 생성
    val list = arrayListOf<String>()
    list.last() // 자바의 list 와 기능은 동일하지만 last() 확장함수를 제공해줌

    // 자바의 HashSet 생성
    val set = hashSetOf<Int>()
}