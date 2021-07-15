package learn.kotlin.study.helloworld

/**
 * @author dkansk924@naver.com
 * @since 2021/07/15
 */

// 코틀린은 클래스의 기본 접근지정자가 public 임
class Person(val name: String, var age: Int)
// name 읽기 전용 프로퍼티 임으로 비공개 필드와 getter 만 제공 (val)
// age 읽기, 쓰기 프로퍼티 임으로 비공개 필드와 getter, setter 제공 (var)


fun main() {
    val person = Person("Jae Joon", 99)
    person.age = 26 // 직접 접근하는 것 같지만 사실 setter 가 동작
    println(person.name)  // 직접 접근하는 것 같지만 사실 getter 가 동작
    println(person.age)
}