package cao.cuong.supership.supership.data.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @author at-cuongcao.
 */
data class Menu(@SerializedName("drinks") var drinks: MutableList<Drink>,
                @SerializedName("sub_menus") var subMenus: MutableList<SubMenu>)
