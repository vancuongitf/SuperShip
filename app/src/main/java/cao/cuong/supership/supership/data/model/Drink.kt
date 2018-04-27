package cao.cuong.supership.supership.data.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @author at-cuongcao.
 */
data class Drink(@SerializedName("drink_id") var id: Long,
                 @SerializedName("drink_menu_id") var menuId: Long?,
                 @SerializedName("drink_name") var name: String,
                 @SerializedName("drink_price") var price: Int,
                 @SerializedName("drink_image") var image: String,
                 @SerializedName("drink_options") var options: MutableList<DrinkOption>)
