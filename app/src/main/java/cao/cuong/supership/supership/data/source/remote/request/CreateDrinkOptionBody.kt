package cao.cuong.supership.supership.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class CreateDrinkOptionBody(@SerializedName("token") var token: String,
                                 @SerializedName("store_id") var storeId: Long,
                                 @SerializedName("multi_choose") var multiChoose: Int,
                                 @SerializedName("name") val name: String)
