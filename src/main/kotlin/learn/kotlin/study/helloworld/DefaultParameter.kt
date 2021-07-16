package learn.kotlin.study.helloworld

/**
 * @author dkansk924@naver.com
 * @since 2021/07/16
 */


@JvmOverloads // 맨 마지막 파라미터로부터 파라미터를 하나씩 생략한 오버로딩한 자바 메서드 추가
//ex: helloMethod(Collection collection), helloMethod(Collection collection, String prefix) ......
fun <T> helloMethod(
    collection: Collection<T>,
    prefix: String = "hello", // 디폴트 파라미터값을 설정할 수 있음
    suffix: String = "!"
) {
    for (item in collection) {
        println("$prefix $item $suffix")
    }
}

fun main() {
    val list = listOf("kjj", "jaejoon", "Member")
    helloMethod(list)  // 인자를 넘기지않으면 디폴드 메서드가 작동
    helloMethod(list, "HI")  // 인자를 넘기지않으면 디폴드 메서드가 작동
    helloMethod(list, "Hi", "😀") //이런식으로 인자를 넘기는건 헷갈릴 수 있음으로 !

    // 이런식으로 이름을 붙여 사용하는것이 좋다(인자를 명시했다면 나머지 인자도 반드시 명시해야한다)
    helloMethod(suffix = "!", collection = list, prefix = "hi")
}