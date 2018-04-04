package cao.cuong.supership.supership.data.model

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

/**
 *
 * @author at-cuongcao.
 */
data class StoreInfoExpress(@SerializedName("store_id") val storeId: Long,
                            @SerializedName("store_name") val storeName: String,
                            @SerializedName("store_address") val storeAddress: String,
                            @SerializedName("store_lat_lng") val storeLatLng: LatLng,
                            @SerializedName("store_distance") val storeDistance: Long?,
                            @SerializedName("store_rate") val storeRate: StoreRate,
                            @SerializedName("store_image") val storeImage: String)
