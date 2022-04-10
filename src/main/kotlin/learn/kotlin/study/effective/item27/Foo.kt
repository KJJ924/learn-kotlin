package learn.kotlin.study.effective.item27

const val MIN_PASSWORD_LENGTH = 7


fun isPasswordValid(text: String): Boolean {
    if (text.length < 7) {
        return false
    }
    ///...
    return true;
}


fun main() {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.toast(
    message: String,
    duration: Int = Toast.LENGTH_LONG
) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

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