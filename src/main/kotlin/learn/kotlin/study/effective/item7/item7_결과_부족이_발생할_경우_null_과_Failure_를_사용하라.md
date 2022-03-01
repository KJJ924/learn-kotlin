

# 결과 부족이 발생할 경우 null과 Failure 를 사용하라



함수가 원하는 결과를 만들어 낼 수 없을 때 처리하는 매커니즘은 크게 두 가지가 존재한다.

- <u>**null**</u> 또는 '실패를 나타내는 **<u>sealed 클래스</u>** 를 리턴한다.'
- 예외를 Throw 한다.



## 예외를 Throw 하는 방법

예외를 사용할때 정보를 전달하는 방법으로 사용해서는 안된다.

예외는 잘못된 특별한 상황을 나타내야 하며 정말 예외적인 상황이 발생했을 때 사용하는 것이 좋다. (effective java item 69)

이유는 다음과 같다.

1. 많은 개발자가 예외가 전파되는 과정을 제대로 추적하지 못한다.
2. 코들린의 모든 예외는 unchecked 이다. 따라서 사용자가 처리하지 않을 수도 있으며 이와 관련된 내용은 문서에 제대로 드러나지 않는다.
3. 예외는 예외적인 상황을 처리하기 위해서 만들어졌으므로 명시적인 테스트 만큼 빠르게 동작하지 않는다.
4. try-catch 블록 내부에 코드를 배치하면 컴파일러가 할 수 있는 최적화가 제한된다.



## null 또는 실패를 나타내는 **sealed 클래스** 를 리턴한다.

앞서 살펴본 방식보다 해당 방식은 명시적이고, 효율적이며, 간단한 방법으로 처리할 수 있다.

따라서 충분이 예측할 수 있는 범위의 오류는 null 과 Failure를 사용하고
예측하기 어려운 예외적인 범위의 오류는 예외를 throw 해서 처리 하는 것이 좋다.



**예시**

**null 리턴하는 케이스**

```kotlin
inline fun <reified T> String.readObjectOrNull(): T? { //null 이 발생할 수 있다는 경고를 줄 수 있도록 method name 작성 
    //...
    if(incorrectSign) {
        return null
    }
    //...
    return result
}
```



사용자가 null 을 처리해야 한다면 Elvis 연산자 같은 다양한 널 안정성 기능을 활용하여 이용한다.

```kotlin
var age = userText.readObjectOrNull<Person>?.age ?:-1
```



****

**sealed 을 리턴하는 케이스**

```kotlin
inline fun <reified T : Any> String.isParsing(): Result<T> {
  	//...
    if(incorrectSign){
        return Failure(JsonParsingException()) 
    }
  
    //...
    return Success(result)
}
```

**sealed 클래스**

```kotlin
sealed class Result<out T>
class Success<out T>(val result: T) : Result<T>()
class Failure(val throwable: Throwable): Result<Nothing>()
class JsonParsingException: Exception()
```



다음과 같이 공용체(union type)를 리턴하기로 했다면 when 표현식을 사용해서 이를 처리 할 수 있다.

```kotlin
val person = userText.isParsing<Person>();
val age = when(person) {
    is Success -> person.age // 요기 오타인것 같아요! person.result.age 이렇게 꺼낼 수 있지않나요?
    is Failure -> -1
}
```

이러한 오류 처리 방식은 try-catch 블록보다 효율적이며 사용하기 쉽고 더 명확하다.



## null 값과 sealed result 클래스 사용 차이점

- <u>추가적인 정보를 전달</u>해야 한다면 -> sealed result 사용
    - Failure 는 처리할 때 필요한 정보를 가질 수 있다는 것을 기억
- 그런게 아니라면 null 을 사용







