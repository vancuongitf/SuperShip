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
                       @SerializedName("drink_option_items") val items: MutableList<DrinkOptionItem>) {

    internal var isSelected: Boolean = false

    internal fun getTotalPrice(): Int {
        var price = 0
        items.forEach {
            if (it.isSelected) {
                price += it.price
            }
        }
        return price
    }

    internal fun copy(): DrinkOption {
        val newItems = mutableListOf<DrinkOptionItem>()
        items.forEach {
            newItems.add(it.copy())
        }
        val result = DrinkOption(id, storeId, name, multiChoose, newItems)
        result.isSelected = isSelected
        return result
    }

    internal fun getOrderedOption(items: MutableSet<Long>): DrinkOption {
        val result = copy()
        result.items.clear()
        this.items.forEach {
            if (items.contains(it.id)) {
                val drinkOptionItem = it.copy()
                drinkOptionItem.isSelected = true
                result.items.add(it.copy())
            }
        }
        return result
    }

    internal fun containDrinkOptionItem(drinkOptionItemId: Long): Boolean {
        items.forEach {
            if (it.id == drinkOptionItemId) {
                return true
            }
        }
        return false
    }

//    internal fun getOrderedDrinkOption(drinkOptionItemId: Long): DrinkOption? {
//        if (containDrinkOptionItem(drinkOptionItemId)) {
//            items.forEach {
//                if ()
//            }
//        }
//        return null
//    }
}
