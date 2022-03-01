package learn.kotlin.study.effective.item7

sealed class Result<out T>
class Success<out T>(val result: T) : Result<T>()
class Failure(val throwable: Throwable): Result<Nothing>()