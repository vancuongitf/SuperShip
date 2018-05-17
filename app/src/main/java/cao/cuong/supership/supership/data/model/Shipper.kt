package cao.cuong.supership.supership.data.model

import com.google.gson.annotations.SerializedName

data class Shipper(@SerializedName("id") val id: Long,
                   @SerializedName("full_name") val fullName: String,
                   @SerializedName("birth_day") var birthDay: String,
                   @SerializedName("address") var address: String,
                   @SerializedName("phone_number") var phone: String,
                   @SerializedName("deposit") var deposit: Int,
                   @SerializedName("personal_id") var personalId: String,
                   @SerializedName("email") var email: String)
