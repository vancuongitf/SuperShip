package cao.cuong.supership.supership.data.model.google

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class AutoComplete(@SerializedName("place_id") val placeId: String,
                        @SerializedName("description") val description: String,
                        @SerializedName("structured_formatting") val address: AddressFormat) {

    var latLng: LatLng? = null
}
