package cao.cuong.supership.supership.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng

data class ShipAddress(var latLng: LatLng, var address: String, var distance: Long, var shipRoad: String) : Parcelable {

    companion object CREATOR : Parcelable.Creator<ShipAddress> {
        override fun createFromParcel(parcel: Parcel): ShipAddress {
            return ShipAddress(parcel)
        }

        override fun newArray(size: Int): Array<ShipAddress?> {
            return arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(
            parcel.readParcelable(LatLng::class.java.classLoader),
            parcel.readString(),
            parcel.readLong(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(latLng, flags)
        parcel.writeString(address)
        parcel.writeLong(distance)
        parcel.writeString(shipRoad)
    }

    override fun describeContents(): Int {
        return 0
    }

    internal fun isValid(): Boolean = address.isNotEmpty() && distance > -1 && shipRoad.isNotEmpty()
}
