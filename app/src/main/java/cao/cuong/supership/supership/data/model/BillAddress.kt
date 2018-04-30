package cao.cuong.supership.supership.data.model

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

/**
 *
 * @author at-cuongcao.
 */
data class BillAddress(@SerializedName("user_id") val userId: Long,
                       @SerializedName("address") val address: String,
                       @SerializedName("lat_lng") val latLng: LatLng)
