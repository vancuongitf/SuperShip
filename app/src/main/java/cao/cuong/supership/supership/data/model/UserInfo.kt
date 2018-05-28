package cao.cuong.supership.supership.data.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @author at-cuongcao.
 */
data class UserInfo(@SerializedName("token") val token: String,
                    @SerializedName("user_id") val id: Long,
                    @SerializedName("user_name") val userName: String,
                    @SerializedName("full_name") var fullName: String,
                    @SerializedName("phone") var phoneNumber: String,
                    @SerializedName("email") var email: String,
                    @SerializedName("bill_addresses") val billAddresses: MutableList<BillAddress>)
