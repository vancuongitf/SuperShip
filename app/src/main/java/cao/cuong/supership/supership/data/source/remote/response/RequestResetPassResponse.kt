package cao.cuong.supership.supership.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 *
 * @author at-cuongcao.
 */
data class RequestResetPassResponse(@SerializedName("userId") var userId: Int, @SerializedName("userName") var userName: String)
