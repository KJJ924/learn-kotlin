package learn.kotlin.study.effective.item5


class Account(amount: Long, val name: String) {

    var amount: Long = amount
        private set

    fun payment(amount: Long): Long {
        require(amount > 0) {
            "결제금액이 0 보다 작을수는 없다. amount = '$amount'"
        }
        check(validAmount(amount)) {
            "보유금액보다 더 큰 금액을 결제할 수 없다."
        }
        this.amount -= amount

        assert(this.amount >= 0)

        return this.amount
    }

    fun hyundaiCardPayment(card: Card, amount: Long): Long {
        require(card is HyundaiCard)
        payment(amount - card.payBack(amount))
        card.attendHyundaiEvent() // smart casting 됨
        return this.amount;
    }

    private fun validAmount(amount: Long): Boolean {
        return this.amount >= amount
    }

}

fun main() {
    val account = Account(1000, "jaejoon")
    account.hyundaiCardPayment(HyundaiCard(),1000)
    println(account)
}