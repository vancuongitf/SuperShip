package cao.cuong.supership.supership.data.model

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp
import java.util.*


data class BillLocation(@SerializedName("lat_lng") var latLng: LatLng,
                        @SerializedName("last_modify") var lastModifyTime: Long) {

    internal fun getDate() = Date(Timestamp(lastModifyTime).time)
}
