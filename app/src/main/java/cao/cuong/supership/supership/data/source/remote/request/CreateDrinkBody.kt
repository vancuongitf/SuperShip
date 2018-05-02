package cao.cuong.supership.supership.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class CreateDrinkBody(@SerializedName("token") var token: String?,
                           @SerializedName("store_id") val storeId: Long,
                           @SerializedName("name") var name: String,
                           @SerializedName("un_accent_name") var unAccentName: String,
                           @SerializedName("price") var price: Int,
                           @SerializedName("image") var image: String)
