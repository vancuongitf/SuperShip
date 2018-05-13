package cao.cuong.supership.supership.data.model

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

/**
 *
 * @author at-cuongcao.
 */
data class Store(@SerializedName("store_id") var id: Long,
                 @SerializedName("store_user_id") var ownerId: Long,
                 @SerializedName("store_name") var name: String,
                 @SerializedName("store_address") var address: String,
                 @SerializedName("store_lat_lng") var latLng: LatLng,
                 @SerializedName("store_phone") var phone: String,
                 @SerializedName("store_email") var email: String,
                 @SerializedName("store_image") var image: String,
                 @SerializedName("store_open_time") var openHour: OpenHour,
                 @SerializedName("store_rate") var rate: StoreRate,
                 @SerializedName("menu") var menu: MutableList<Drink>,
                 @SerializedName("options") var options: MutableList<DrinkOption>,
                 @SerializedName("isOpening") var isOpening: Boolean,
                 @SerializedName("store_status") var status: Boolean)
