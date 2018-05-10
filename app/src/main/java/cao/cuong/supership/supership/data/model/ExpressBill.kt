package cao.cuong.supership.supership.data.model

import com.google.gson.annotations.SerializedName

data class ExpressBill(@SerializedName("bill_id") val id: Long,
                       @SerializedName("store_name") val storeName: String,
                       @SerializedName("store_image") val storeImage: String,
                       @SerializedName("status") val status: Int,
                       @SerializedName("price") val price: Int)
