package cao.cuong.supership.supership.data.model

import com.google.gson.annotations.SerializedName

data class ShipperExpress(@SerializedName("id") val id: Long,
                          @SerializedName("full_name") var fullName: String,
                          @SerializedName("address") var address: String,
                          @SerializedName("phone_number") var phoneNumber: String,
                          @SerializedName("email") var email: String,
                          @SerializedName("status") var status: Int)
