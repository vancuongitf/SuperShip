package cao.cuong.supership.supership.data.model.google

import com.google.gson.annotations.SerializedName

data class PlaceDetail(@SerializedName("geometry") val geometry: Geometry)
