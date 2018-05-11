package cao.cuong.supership.supership.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class EditDrinkBody(@SerializedName("token") var token: String?,
                         @SerializedName("drink_id") val drinkId: Long,
                         @SerializedName("name") var name: String,
                         @SerializedName("un_accent_name") var unAccentName: String,
                         @SerializedName("price") var price: Int,
                         @SerializedName("image") var image: String,
                         @SerializedName("add_options") val addOptions: Set<Long>,
                         @SerializedName("delete_options") val deleteOptions: Set<Long>)
