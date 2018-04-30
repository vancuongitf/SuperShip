package cao.cuong.supership.supership.data.model.google

import com.google.gson.annotations.SerializedName

data class GeoCoding(@SerializedName("formatted_address") val address: String)
