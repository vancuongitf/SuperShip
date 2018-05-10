package cao.cuong.supership.supership.data.source.remote.response.google

import cao.cuong.supership.supership.data.model.google.GeoCoding
import com.google.gson.annotations.SerializedName

data class GeoCodingResponse(@SerializedName("results") val results: MutableList<GeoCoding>,
                             @SerializedName("error_message") var error: String?,
                             @SerializedName("place_id") var placeId: String)
