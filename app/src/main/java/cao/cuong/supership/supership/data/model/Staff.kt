package cao.cuong.supership.supership.data.model

import com.google.gson.annotations.SerializedName

data class Staff(@SerializedName("id") val id: Long,
                 @SerializedName("account") val account: String,
                 @SerializedName("full_name") val fullName: String,
                 @SerializedName("email") val email: String,
                 @SerializedName("phone_number") val phoneNumber: String,
                 @SerializedName("birth_day") val birthDay: String,
                 @SerializedName("address") val address: String,
                 @SerializedName("personal_id") val personalId: String,
                 @SerializedName("status") val status: Int)
