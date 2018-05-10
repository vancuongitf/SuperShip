package cao.cuong.supership.supership.data.model.google

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class Location(@SerializedName("lat") val lat: Double,
                    @SerializedName("lng") val lng: Double) {

    internal fun toLatLng() = LatLng(lat, lng)
}
