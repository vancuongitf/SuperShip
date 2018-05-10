package cao.cuong.supership.supership.data.model

import cao.cuong.supership.supership.extension.isSameOther
import com.google.gson.annotations.SerializedName

data class OrderDrink(@SerializedName("drink_id") val drinkId: Long,
                      @SerializedName("count") var count: Int,
                      @SerializedName("optionals") val optionals: MutableSet<OptionalBody>,
                      @SerializedName("note") var note: String) {
    internal fun isSameWithOther(orderDrink: OrderDrink) =
            drinkId == orderDrink.drinkId && note == orderDrink.note && optionals.isSameOther(orderDrink.optionals)
}
