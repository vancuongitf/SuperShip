package cao.cuong.supership.supership.data.source.remote.request

import com.google.gson.annotations.SerializedName

/**
 *
 * @author at-cuongcao.
 */
data class CreateUserBody(@SerializedName("full_name") var fullName: String,
                          @SerializedName("name") var userName: String,
                          @SerializedName("password") var password: String,
                          @SerializedName("email") var email: String,
                          @SerializedName("phone") var phone: String,
                          @SerializedName("is_shipper") var isShipper: Int = 0,
                          @SerializedName("status") var status: Int = 0)
