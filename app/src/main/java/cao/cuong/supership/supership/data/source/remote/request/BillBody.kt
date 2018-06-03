package cao.cuong.supership.supership.data.source.remote.request

import cao.cuong.supership.supership.data.model.OrderedDrink
import cao.cuong.supership.supership.data.model.google.StoreAddress
import com.google.gson.annotations.SerializedName

data class BillBody(@SerializedName("store_id") val storeId: Long,
                    @SerializedName("user_id") var userId: Long,
                    @SerializedName("user_name") var userName: String,
                    @SerializedName("user_phone") var phone: String,
                    @SerializedName("address") var address: StoreAddress?,
                    @SerializedName("ship_price") var shipPrice: Int,
                    @SerializedName("ship_road") var shipRoad: String,
                    @SerializedName("drinks") val orderedDrinks: MutableList<OrderedDrink>)
