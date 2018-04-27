package cao.cuong.supership.supership.data.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @author at-cuongcao.
 */
data class UserInfo(@SerializedName("user_id") val id: Long,
                    @SerializedName("full_name") var fullName: String,
                    @SerializedName("phone_number") var phoneNumber: String,
                    @SerializedName("email") var email: String,
                    @SerializedName("bill_addresses") val billAddresses: MutableList<BillAddress>)
