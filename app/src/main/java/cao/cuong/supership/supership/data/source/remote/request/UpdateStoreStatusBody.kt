package cao.cuong.supership.supership.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class UpdateStoreStatusBody(@SerializedName("token") var token: String,
                                 @SerializedName("store_id") var id: Long,
                                 @SerializedName("new_status") var newStatus: Int)
