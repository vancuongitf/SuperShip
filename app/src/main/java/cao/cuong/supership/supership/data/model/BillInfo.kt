package cao.cuong.supership.supership.data.model

import cao.cuong.supership.supership.BuildConfig
import com.google.gson.annotations.SerializedName

data class BillInfo(@SerializedName("bill_id") val id: Long,
                    @SerializedName("store") val store: StoreInfo,
                    @SerializedName("bill_user_id") val userId: Long,
                    @SerializedName("bill_user_name") val customerName: String,
                    @SerializedName("bill_user_phone") val customerPhone: String,
                    @SerializedName("bill_address") val address: Address,
                    @SerializedName("bill_ship_road") val shipRoad: String,
                    @SerializedName("bill_shipper_id") val shipperId: Long,
                    @SerializedName("bill_time") val time: Long,
                    @SerializedName("bill_price") val price: Int,
                    @SerializedName("bill_ship_price") val shipPrice: Int,
                    @SerializedName("bill_status") val status: Int,
                    @SerializedName("online_payment") var onlinePayment: Int,
                    @SerializedName("confirm_code") val confirmCode: Int,
                    @SerializedName("bill_complete_time") val completeTime: Long,
                    @SerializedName("drinks") val drinks: MutableList<OrderedDrink>) {

    internal fun getUSDPrice() = Math.ceil((shipPrice + price).toDouble() / BuildConfig.USD_EXCHANGE_RATE * 100) / 100

    internal fun isOnlinePayment() = onlinePayment == 1
}

data class StoreInfo(@SerializedName("id") val id: Long,
                     @SerializedName("name") val name: String,
                     @SerializedName("address") val address: Address,
                     @SerializedName("image") val image: String)

