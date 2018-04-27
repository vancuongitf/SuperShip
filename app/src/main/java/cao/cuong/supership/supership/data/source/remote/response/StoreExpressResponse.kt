package cao.cuong.supership.supership.data.source.remote.response

import cao.cuong.supership.supership.data.model.StoreInfoExpress
import com.google.gson.annotations.SerializedName

/**
 *
 * @author at-cuongcao.
 */
data class StoreExpressResponse(@SerializedName("next_page_flag") var nextPageFlag: Boolean,
                                @SerializedName("store_list") var storeList: MutableList<StoreInfoExpress>)
