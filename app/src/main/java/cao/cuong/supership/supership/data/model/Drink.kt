package cao.cuong.supership.supership.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *
 * @author at-cuongcao.
 */
data class Drink(@SerializedName("drink_id") var id: Long,
                 @SerializedName("drink_menu_id") var menuId: Long?,
                 @SerializedName("drink_name") var name: String,
                 @SerializedName("drink_price") var price: Int,
                 @SerializedName("drink_image") var image: String,
                 @SerializedName("order_count") var orderCount: Int,
                 @SerializedName("drink_options") var options: MutableSet<Long>) : Serializable {

    internal fun copy(): Drink {
        val newOptions = mutableSetOf<Long>()
        newOptions.addAll(options)
        return Drink(id, menuId, name, price, image, orderCount, newOptions)
    }
}
