package cao.cuong.supership.supership.data.model

import com.google.gson.annotations.SerializedName

data class OptionalBody(@SerializedName("drink_option_id") var drinkOptionsId: Long,
                        @SerializedName("items") val options: MutableSet<Long>)
