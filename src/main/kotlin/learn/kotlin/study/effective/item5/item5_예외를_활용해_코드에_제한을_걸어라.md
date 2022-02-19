# 예외를 활용해 코드에 제한을 걸어라

-----

확실한 동작을 보장해야하는 코드라면 예외를 활용해 제한을 거는 것이 좋다.

코틀린은 다음과 같은 방법을 사용할 수 있다.

- require
    - 아규먼트를 제한할때 사용
    - 조건을 만족하지 않는다면 `IllegalArgumentException` 발생
- check
    - 상태와 관련된 동작을 제한할때 사용
    - 조건을 만족하지 않는다면 `IllegalStateException`  발생
- assert
    - 어떤 것이 true 인지 확인할때 사용
    - 테스트모드에서만 작동하며 `-ea`  JVM 옵션을 활성화 해야한다.



## 아규먼트

**사용자로 부터 받은 인자를 검증하여 제한할때** 사용한다.

```kotlin
class Account(amount: Long, val name: String) {

    var amount: Long = amount
        private set

    fun payment(amount: Long): Long {
        require(amount > 0) {
            "결제금액이 0 보다 작을수는 없다. amount = '$amount'" //lazyMessage 이용하여 메세지를 정의할 수 있다.
        }
        this.amount -= amount
        return this.amount
    }
}
```

입력 유효성 검사 코드(require) 메서드 앞부분에 배치되므로 읽는 사람도 쉽게 확인할 수 있다.
하지만 코드를 사용하는 모든 사람이 실제 코드를 모두 읽어보는것은 아님으로 문서에 관련된 내용을 반드시 명시해 두어야 한다.



## 상태

어떠한 조건을 만족할때만 메서드를 이용할 수 있게 해야할때 사용 (지정된 예측을 만족하지 못할 때)

```kotlin
class Account(amount: Long, val name: String) {

    var amount: Long = amount
        private set

    fun payment(amount: Long): Long {
        check(validAmount(amount)) {
            "보유금액보다 더 큰 금액을 결제할 수 없다."
        }
        this.amount -= amount

        return this.amount
    }

    private fun validAmount(amount: Long): Boolean {
        return this.amount >= amount
    }
}
```

사용자가 코드를 제대로 사용할 거라고 믿고 있는 것보다 항상 문제 상황을 예측하고 문제 상황에 예외를 throw 하는 것이 좋다.



## Assert 계열 함수 사용

단위 테스트 구현의 정확성을 확인하는 가장 기본적인 방법

```kotlin
class Account(amount: Long, val name: String) {

    var amount: Long = amount
        private set

    fun payment(amount: Long): Long {
        check(validAmount(amount)) 
        this.amount -= amount

        assert(this.amount >= 0) // 테스트모드 에서만 작동함 
        return this.amount
    }

    private fun validAmount(amount: Long): Boolean {
        return this.amount >= amount
    }
}
```

테스트모드 이외의 실행에서는 해당 코드는 작동하지 않는다.
(테스트이외의 실행에는 코드가 실행되지 않아도 실행코드에 테스트코드가 포함되는 것보다 분리되어 관리되는 것이 좋지않나요!?🤔)



## Nullability 와 스마트 캐스팅

코틀린은 require, check 블록으로 어떤 조건을 확인하여 true 가 나왔다면 해당 조건은 이후로도 true 이다.

따라서 이를 이용해 타입비교( `is` )를 했다면 스마트캐스트가 작동한다.

```kotlin
class Account(amount: Long, val name: String) {

    var amount: Long = amount
        private set

    fun payment(amount: Long): Long {
	     //... 생략
    }

    fun hyundaiCardPayment(card: Card, amount: Long): Long {
        require(card is HyundaiCard)
        payment(amount - card.payBack(amount))
        card.attendHyundaiEvent() // smart casting 됨
        return this.amount;
    }
}
```



`requireNotNull()`,`checkNotNull()` 을 이용하여 변수를 <u>unpack</u> 하는 용도로 활용할 수도있다.

```kotlin
class SenderService(val sender: Sender) {
  fun sender(member: Member, content: String){
    val email = requireNotNull(member.email) // member 의 email 이 null 이 아니라면 email 을 반환하여 변수를 꺼내준다.
    sender.send(email, content)
  }
}
```

위 예제 처럼 null 인 경우 예외 발생이 아닌 단순히 함수를 종료하고 싶다면 Elvis 연산자(`?:`) 을 이용하여 유연하게 변경할 수 있다.

```kotlin
class SenderService(val sender: Sender) {
  fun sender(member: Member, content: String){
    val email = member.email ?: return // 오류를 발생시키지 않고 함수 중지
    val email = member.email ?: run { // 함수를 중지 시키기 전에 log 를 남기고싶다면 run 함수를 이용하면 된다.
      log("회원에 이메일이 등록되지 않음")
     	return
    }
    sender.send(email, content)
  }
}
```

return 과 throw를 활용한 Elvis 연산자는 nullable 을 확인할때 많이 사용되는 관용적인 방법이다.



## 정리

코틀린에서 지원하는 제한 함수를 사용하면 다음과 같은 이점이 있다.

- 제한을 훨씬 더 쉽게 확인 할 수 있다.
- 애플리케이션을 더 안정적으로 지킬 수 있다.
- 코드를 잘못 쓰는 상황을 막을 수 있다.
- 스마트 캐스팅을 활용할 수 있다.

