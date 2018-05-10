package cao.cuong.supership.supership.data.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class OrderedDrink(@SerializedName("id") var id: Long,
                        @SerializedName("drink_id") var drinkId: Long,
                        @SerializedName("drink_name") var name: String,
                        @SerializedName("drink_price") var price: Int,
                        @SerializedName("drink_image") var image: String,
                        @SerializedName("count") var count: Int,
                        @SerializedName("note") var note: String,
                        @SerializedName("drink_options") var options: MutableList<DrinkOption>) {

    internal fun sameWithOther(orderedDrink: OrderedDrink) = drinkId == orderedDrink.drinkId
            && note == orderedDrink.note
            && Gson().toJson(options).toString() == Gson().toJson(orderedDrink.options).toString()
}
