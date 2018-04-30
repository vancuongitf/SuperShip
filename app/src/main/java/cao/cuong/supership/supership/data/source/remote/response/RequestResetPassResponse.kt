package cao.cuong.supership.supership.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 *
 * @author at-cuongcao.
 */
data class RequestResetPassResponse(@SerializedName("user_id") var userId: Int, @SerializedName("user_name") var userName: String)
