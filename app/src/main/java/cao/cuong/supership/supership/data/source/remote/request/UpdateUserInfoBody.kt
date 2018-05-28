package cao.cuong.supership.supership.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class UpdateUserInfoBody(@SerializedName("user_id") val userId: Long,
                              @SerializedName("name") var newName: String,
                              @SerializedName("phone") var newPhone: String)
