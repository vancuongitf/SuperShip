package cao.cuong.supership.supership.data.model.google

import com.google.gson.annotations.SerializedName

data class AddressFormat(@SerializedName("main_text") val mainText: String?,
                         @SerializedName("secondary_text") val secondaryText: String?)