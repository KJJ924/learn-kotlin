package learn.kotlin.study.effective.item5

class HyundaiCard : Card {

    override fun payBack(amount: Long): Long {
        //i -> i * 2 / 100
        return amount * 2 / 100;
    }

    fun attendHyundaiEvent(){
        println("현대카드 이벤트 참여")
    }
}