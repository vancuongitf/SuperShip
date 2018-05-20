package cao.cuong.supership.supership.data.source.remote.response

import cao.cuong.supership.supership.data.model.ShipperExpress
import com.google.gson.annotations.SerializedName

data class ExpressShipperResponse(@SerializedName("next_page_flag") val nextPageFlag: Boolean,
                                  @SerializedName("bill_list") val shippers: MutableList<ShipperExpress>)
