package cao.cuong.supership.supership.data.model.google

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName


data class Direction(@SerializedName("routes") var routes: MutableList<Route>,
                     @SerializedName("status") var status: String)

data class Route(@SerializedName("legs") val legs: MutableList<Leg>,
                 @SerializedName("bounds") var bounds: Bound,
                 @SerializedName("overview_polyline") var polyLine: Polyline)

data class Bound(@SerializedName("northeast") var northeast: Location,
                 @SerializedName("southwest") var southwest: Location)

data class Polyline(@SerializedName("points") var points: String) {

    internal fun decodePoly(): List<LatLng> {

        val poly = ArrayList<LatLng>()
        var index = 0
        val len = points.length
        var lat = 0
        var lng = 0

        while (index < len - 1) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = points[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = points[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val p = LatLng(lat.toDouble() / 1E5,
                    lng.toDouble() / 1E5)
            poly.add(p)
        }
        return poly
    }
}

data class Leg(@SerializedName("distance") var distance: Distance,
               @SerializedName("end_address") var endAddress: String)

data class Distance(@SerializedName("text") var text: String,
                    @SerializedName("value") var value: Long)
