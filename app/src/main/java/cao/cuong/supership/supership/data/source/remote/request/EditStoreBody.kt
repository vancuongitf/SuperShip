package cao.cuong.supership.supership.data.source.remote.request

import cao.cuong.supership.supership.data.model.OpenHour
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class EditStoreBody(@SerializedName("id") var id: Long,
                         @SerializedName("token") var token: String,
                         @SerializedName("store_name") var name: String,
                         @SerializedName("store_un_accent_name") var unAccentName: String,
                         @SerializedName("store_address") var address: String,
                         @SerializedName("store_lat_lng") var latLng: LatLng,
                         @SerializedName("store_phone") var phone: String,
                         @SerializedName("store_email") var email: String,
                         @SerializedName("store_image") var image: String,
                         @SerializedName("store_open_time") var openHour: OpenHour) {
    internal fun sameWithOther(editStoreBody: EditStoreBody) = id == editStoreBody.id
            && name == editStoreBody.name
            && address == editStoreBody.address
            && latLng.latitude == editStoreBody.latLng.latitude
            && latLng.longitude == editStoreBody.latLng.longitude
            && phone == editStoreBody.phone
            && openHour.sameWithOther(editStoreBody.openHour)
}
