package cao.cuong.supership.supership.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class CreateShipperBody(@SerializedName("full_name") var fullName: String,
                             @SerializedName("account") var account: String,
                             @SerializedName("password") var password: String,
                             @SerializedName("personal_id") var personalId: String,
                             @SerializedName("address") var address: String,
                             @SerializedName("birth_day") var doB: String,
                             @SerializedName("phone_number") var phone: String,
                             @SerializedName("email") var email: String)
