package cao.cuong.supership.supership.data.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @author at-cuongcao.
 */
data class DrinkOptionItem(@SerializedName("drink_option_item_id") var id: Long,
                           @SerializedName("drink_option_id") var drinkOptionId: Long,
                           @SerializedName("drink_option_item_name") var name: String,
                           @SerializedName("drink_option_item_price") var price: Int) {
    internal var isSelected = false

    internal fun copy(): DrinkOptionItem {
        val rs = DrinkOptionItem(id, drinkOptionId, name, price)
        rs.isSelected = isSelected
        return rs
    }
}
