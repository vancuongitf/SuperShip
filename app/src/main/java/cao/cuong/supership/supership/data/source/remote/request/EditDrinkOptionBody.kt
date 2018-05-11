package cao.cuong.supership.supership.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class EditDrinkOptionBody(@SerializedName("token") var token: String,
                               @SerializedName("option_id") val id: Long,
                               @SerializedName("name") var name: String,
                               @SerializedName("multi_choose") var multiChoose: Int,
                               @SerializedName("delete_items") val deleteItems: MutableSet<Long>,
                               @SerializedName("add_items") val addItems: MutableList<DrinkOptionItemBody>)
