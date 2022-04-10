package learn.kotlin.study.effective.item27

import learn.kotlin.study.effective.item27.MessageLength.*

interface MessageDisplay {
    fun show(
        message:String,
        duration: MessageLength = LONG
    )
}