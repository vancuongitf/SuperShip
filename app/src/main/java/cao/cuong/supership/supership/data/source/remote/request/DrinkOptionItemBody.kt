package cao.cuong.supership.supership.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class DrinkOptionItemBody(@SerializedName("drink_option_id") var optionId: Long,
                               @SerializedName("name") var name: String,
                               @SerializedName("price") var price: Int)
