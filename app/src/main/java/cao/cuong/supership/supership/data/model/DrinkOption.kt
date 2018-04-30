package cao.cuong.supership.supership.data.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @author at-cuongcao.
 */
data class DrinkOption(@SerializedName("drink_option_id") var id: Long,
                       @SerializedName("drink_option_store_id") var storeId: Long,
                       @SerializedName("drink_option_name") var name: String,
                       @SerializedName("drink_option_mutil_choose") var multiChoose: Int,
                       @SerializedName("drink_option_items") var items: MutableList<DrinkOptionItem>)
