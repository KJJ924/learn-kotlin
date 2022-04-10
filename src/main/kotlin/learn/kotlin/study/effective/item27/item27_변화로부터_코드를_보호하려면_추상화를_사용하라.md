# 변화로부터 코드를 보호하려면 추상화를 사용하라



### 추상화를 하는 몇 가지의 방법

- 상수로 추출
- 동작을 함수로 래핑한다.
- 힘수를 클래스로 래핑한다.
- 인터페이스 뒤에 클래스를 숨긴다.
- 보편적인 객체를 특수한 객체로 래핑한다.



## 상수로 추출

```kotlin
fun isPasswordValid(text: String): Boolean {
    if (text.length < 7) {
        return false
    }
    ///...
}
```

다음 코드에서 `text.length < 7` 은  비밀번호의 최소 길이를 뜻하지만 이해하는데에는 시간이 걸린다.

```kotlin
const val MIN_PASSWORD_LENGTH = 7
```

다음과 같은 코드를 상수로 빼어내어 이름을 준다면 코드를 이해하기에 도움을 줄 수 있다.

따라서 상수로 추출하면 다음과 같은 이득이 있다.

- 이름을 붙일 수 있고
- 나중에 해당값을 쉽게 변경할 수 있다.



## 함수

많이 사용되는 알고리즘은 간단한 확장 함수로 만들어서 사용할 수 있다.

```kotlin
Toast.makeText(this, message, Toast.LENGTH_LONG).show()
```

다음과 같이 토스트 형태의  메세지를 출력하는 코드가 있을때 간단한 확장 함수로 만들어서 재사용 할 수 있도록 하자.

```kotlin
fun Context.toast(
    message: String,
    duration: Int = Toast.LENGTH_LONG
){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
```

만약 여기서 스낵바 형태로 만약 메세지를 출력하고 싶다면 Context.snackbar() 라고 메서드 이름을 변경하고 코드를 수정할 것이다.

```kotlin
fun Context.snackbar(
    message: String,
    duration: Int = Toast.LENGTH_LONG // 토스트형태의 메세지를 출력하는 파라미터를 그대로 사용한다.
){
	//...
}
```

이렇게 이름을 직접 바꾸는 것은 매우 위험하다

- 다른 모듈이 이 함수에 의존하고 있다면 다른 모듈에 문제가 발생한다.

- 또한 이름은 변경하기 쉽지만 파라미터는 한번에 변경하기 힘들다.



여기까지 왔다면 클라이언트측에서 메세지 출력 형식이 중요한게 아니라 <u>**사용자에게 메세지를 출력하고자하는 의도**</u>가 더 중요하다.

따라서 출력 형태의 개념과는 무관한 showMessage 라는 높은 함수로 옮겨보자.

```kotlin
fun Context.showMessage(
    message: String,
    duration: MessageLength = MessageLength.Long
) {
    val toastDuration = when (duration) {
        SHORT -> Length.LENGTH_SHORT
        LONG -> Length.LENGTH_LONG
    }
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

enum class MessageLength { SHORT, LONG }
```

여기서 가장 큰 변화는 함수의 이름이지만 사람관점에서 의미 있는 이름은 어떤 추상화를 표현하고 있는지 알려준다.

따라서 의미 있는 이름은 굉장히 중요하다.



## 클래스

앞선 함수의 추상화는 상태를 유지하지 않는다. 또한 함수 시그니처를 변경하면 프로그램 전체에 영향을 줄 수 있다.

하지만 클래스는 상태를 가질 수 있으며 많은 함수를 가질 수 있다.

```kotlin
class MessageDisplay(val context: Context) {
    fun show(
        message: String,
        duration: MessageLength = MessageLEngth.LONG
    ) {
        val toastDuration = when(duration) {
            SHORT -> Length.LENGTH_SHORT
            LONG -> Length.LENGTH_LONG
        }
        Toast.makeText(context, message, toastDuration).show()
    }
}
enum class MessageLength { SHORT, LONG }

val messageDisplay = MessageDisplay(context) //DI 가능
messageDisplay.show("Message")
```

이런식으로 클래스로 추상화 한다면 다음과 같은 장점을 가져갈 수 있다.

- mock 객체를 사용하여 테스트가 용이하다.
- 의존성 주입 프레임워크 이용하면 클래스 생성을 위임할 수 있다.(IOC)
- 메세지를 출력하는 더 다양한 종류의 메서드를 만들 수 있다.

하지만 여전히 해당 클래스 타입 아래에 어떤 구현이 있는지 알 수 있다.

더 많은 자유를 얻으려면 인터페이스 뒤에 클래스를 숨기면 된다.



## 인터페이스

라이브러리를 만드는 사람은 인터페이스만 유지한다면 별도의 걱정 없이 자신이 원하는 형태로 구현을 변경할 수 있다.

즉 인터페이스 뒤에 객체를 숨김으로써 실질적인 구현은 추상화하고 사용자가 추상화된 것에만 의존하게 만들 수 있다.(결합도 낮춤)

```kotlin
interface MessageDisplay {
    fun show(
        message:String,
        duration: MessageLength = LONG
    )
}
```



```kotlin
class ToastDisplay(val context: Context): MessageDisplay {
    override fun show(
        message: String,
        duration: MessageLength = MessageLEngth.LONG
    ) {
        val toastDuration = when(duration) {
            SHORT -> Length.LENGTH_SHORT
            LONG -> Length.LENGTH_LONG
        }
        Toast.makeText(context, message, toastDuration).show()
    }
}
```

이렇게 구성하면 토스트, 스낵바를 출력하는걸 선택하여 얼마든지 사용할 수 있다.

또 다른 장점은 테스트할 때 인터페이스 페이킹이 클래스 모킹보다 간단하므로 별도의 모킹 라이브러리르 사용하지 않아도 된다.

마지막으로 선언과 사용이 분리되어 있음으로 실제 클래스를 자유롭게 변경할 수 있다.



## 추상화의 문제

추상화는 거의 무한하게 할 수 있지만 어느 순간부터 득보다 실이 많아진다.

추상화를 과도하게 적용하게된다면 코드가 복잡해지고 코드를 이해하는것이 매우 어려워진다.





## 어떻게 군형을 맞춰야할까?

다음과 같은요소에 따라 달라질 수 있다.

- 팀의 크기
- 팀의 경험
- 프로젝트의 크기
- 특징 세트
- 도메인 지식

적절한 균형을 찾는 것은 거의 감각에 의존해야 하는 예술에 가깝다.

수백 시간 이상의 경험이 있어야 할 수 있는 일이다. 그래도 사용할 수 있는 몇 가지 규칙을 정리하자면 다음과 같다.

- 많은 개발자가 참여하는 프로젝트는 이후에 객체 생성과 사용방법을 변경하기 어렵다
  따라서 추상화 방법을 사용하는것이 좋다. 최대한 모듈과 부분을 분리하는 것이 좋다.
- 의존성 주입 프레임워크를 사용하면, 생성이 얼마나 복잡한지는 신경쓰지 않아도 된다.
- 테스트를 하거나 다른 애플리케이션 기반으로 새로운 애플리케이션을 만든다면 추상화를 사용하는 것이 좋다.
- 프로젝트가 작고 실험적이라면 추상화를 하지않고도 직접 변경해도 괜찮다.



## 정리

추상화는 단순하게 중복성을 제거해서 코드를 구성하기 위한 것이 아니다.

추상화는 코드를 변경해야 할 때 도움이 된다.

따라서 추상화를 사용하는 것은 굉장히 어렵지만 이를 배우고 이해해야 한다.

추상적인 구조를 사용하면 결과를 이해하기 어렵지만 추상화를 사용할 때의 장점과 단점을 모두 이해하고

프로젝트 내에서 그 균형을 찾아야한다.

**추상화가 너무 많거나 적은 상황 모두 좋은 상황이 아니다.**

