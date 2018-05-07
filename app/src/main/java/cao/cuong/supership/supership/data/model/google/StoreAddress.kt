package cao.cuong.supership.supership.data.model.google

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

class StoreAddress(
        @SerializedName("address") var address: String,
        @SerializedName("lat_lng") val latLng: LatLng) : Parcelable {

    companion object CREATOR : Parcelable.Creator<StoreAddress> {
        override fun createFromParcel(parcel: Parcel): StoreAddress {
            return StoreAddress(parcel)
        }

        override fun newArray(size: Int): Array<StoreAddress?> {
            return arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(LatLng::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeParcelable(latLng, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    internal fun isInvalid() = address.isNotEmpty() && (latLng != null)
}
