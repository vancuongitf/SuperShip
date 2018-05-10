package cao.cuong.supership.supership.data.model

import cao.cuong.supership.supership.data.model.google.StoreAddress
import com.google.gson.annotations.SerializedName

data class Bill(@SerializedName("id") var id: Long?,
                @SerializedName("store_id") val storeId: Long,
                @SerializedName("user_id") var userId: Long?,
                @SerializedName("user_name") var userName: String,
                @SerializedName("user_phone") var phone: String,
                @SerializedName("address") var address: StoreAddress,
                @SerializedName("shipper_id") var shipperId: Long?,
                @SerializedName("time") var time: Long,
                @SerializedName("price") var price: Int,
                @SerializedName("ship_price") var shipPrice: Int,
                @SerializedName("status") var status: Int,
                @SerializedName("complete_time") var completeTime: Long,
                @SerializedName("drinks") val mutableList: MutableList<Drink>)
