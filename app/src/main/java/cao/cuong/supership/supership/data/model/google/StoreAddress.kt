package cao.cuong.supership.supership.data.model.google

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng

class StoreAddress(val address: String, val latLng: LatLng) : Parcelable {
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

    companion object CREATOR : Parcelable.Creator<StoreAddress> {
        override fun createFromParcel(parcel: Parcel): StoreAddress {
            return StoreAddress(parcel)
        }

        override fun newArray(size: Int): Array<StoreAddress?> {
            return arrayOfNulls(size)
        }
    }
}
