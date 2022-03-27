# 일반적인 프로퍼티 패턴은 프로퍼티 위임으로 만들어라

----



코틀린은 <u>프로퍼티 위임</u>이라는 새로운 기능을 제공한다.

프로퍼티 위임을 사용하면 일반적인 프로퍼티의 행위를 추출해서 재사용 할 수 있다.



**대표적인 예**

- Lazy (처음 요청이 들어올때 초기화)

  ```kotlin
  val value:String by lazy { "init" }
  ```

  이 처럼 코틀린은 `stdlib` 를 이용하여 lazy 프로퍼티 패턴을 쉽게 구현할 수 있게 lazy 함수를 제공한다.



해당코드를 자바로 변환하면 아래와 같습니다

![image](https://user-images.githubusercontent.com/64793712/160292256-e2769e35-6437-497c-9771-e545d53e7eba.png)

`String` 타입으로 생성되는 것이 아니라  `Lazy` 필드인 value$delegate 가 만들어 집니다.

top-level property 임으로 static 블럭안에서 초기화가 일어나는데 `LazyKt.lazy()` 를 이용하여 초기화를 진행하는거 같습니다 !

그 이후  `getValue()` 호출시 `Lazy` 의 `gatValue()` 를 호출되면서 실제 초기화된 값을 가져오게 됩니다.

(초기화를 진행할때 단 하나의 스레드 에서 만 처리되서 thread safe 하다고 합니다.)

![image](https://user-images.githubusercontent.com/64793712/160293166-49866722-a000-4ce5-8b01-6bd7bb822338.png)





- observable (변화가 있을 때 감지)

  ```kotlin
  var key: String? by Delegates.observable(null) { _, oldValue, newValue ->
      println("key changed from $oldValue to $newValue")
  }
  ```

  해당 observable 도  `stdlib` 에 이미 구현 되어 있으며

  자바 코드로 변경했을때 `ObservableProperty` 로 감싸져서 callback 을 받을 수 있게 되어 있습니다.

  ![image](https://user-images.githubusercontent.com/64793712/160294223-3f966b62-3083-41ee-816f-b34fc7f4e8d1.png)

----

## 간단한 프로퍼티 위임 만들기

```kotlin
private class LoggingProperty<T>(var value: T) {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): T {
        println("${prop.name} get value $value")
        return value
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, newValue: T) {
        println("${prop.name} set value ${this.value} -> $newValue")
        this.value = newValue
    }
}
```

간단한 로그를 출력하고 싶다고 가장 했을때 위와 같이 작성하면 되는데 여기서 주의해야 할 점은
메서드 이름을 게터는 `gatValue()`   세터는 `setValue()`  만들어야 한다.

## 

---

## 정리

프로퍼티 위임은 프로퍼티 패턴을 추출하는 일반적인 방법이라 많이 사용된다.

따라서 코틀린 개발자라면 프로퍼티 위임이라는 강력한 도구와 관련된 내용을 잘 알고 있어야 한다.

이를 잘 알면, 일반적인 패턴을 추출하거나 더 좋은 API 를 만들 때 활용할 수 있다.



stdlib 에서  다음과 같은 델리케이터를 알아두면 좋다

- lazy
- Delegates.observable
- Delegates.vetoable
- Delegates.notNull