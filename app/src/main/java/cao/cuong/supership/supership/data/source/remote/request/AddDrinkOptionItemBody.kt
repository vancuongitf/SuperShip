package cao.cuong.supership.supership.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class AddDrinkOptionItemBody(@SerializedName("token") var token: String,
                                  @SerializedName("items") val items: MutableList<DrinkOptionItemBody>)
