package cao.cuong.supership.supership.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class CurrentLocationBody(@SerializedName("token") var token: String,
                               @SerializedName("lat") var lat: Double,
                               @SerializedName("lng") var lng: Double)
