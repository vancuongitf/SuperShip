package cao.cuong.supership.supership.data.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @author at-cuongcao.
 */
data class SubMenu(@SerializedName("menu_id") var id: Long,
                   @SerializedName("menu_name") var name: String,
                   @SerializedName("menu_store_id") var storeId: Long,
                   @SerializedName("drinks") var drinks: MutableList<Drink>)
