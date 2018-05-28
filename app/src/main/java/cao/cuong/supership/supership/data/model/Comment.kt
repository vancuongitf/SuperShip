package cao.cuong.supership.supership.data.model

import com.google.gson.annotations.SerializedName

data class Comment(@SerializedName("id") val id: Long,
                   @SerializedName("user_id") val userId: Long,
                   @SerializedName("user_full_name") val userName: String,
                   @SerializedName("comment") val comment: String,
                   @SerializedName("comment_time") val time: Long)
