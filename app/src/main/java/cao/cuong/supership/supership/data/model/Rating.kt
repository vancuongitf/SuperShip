package cao.cuong.supership.supership.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Rating(@SerializedName("value") val value: Int,
                  @SerializedName("rating_count") var count: Int,
                  @SerializedName("is_selected") var isSelected: Boolean = false):Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte()) {
    }

    internal fun getMessage() = when (value) {
        1 -> "Rất tệ"

        2 -> "Tệ"

        3 -> "Tương đối"

        4 -> "Tốt"

        else -> "Rất tốt"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(value)
        parcel.writeInt(count)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Rating> {
        override fun createFromParcel(parcel: Parcel): Rating {
            return Rating(parcel)
        }

        override fun newArray(size: Int): Array<Rating?> {
            return arrayOfNulls(size)
        }
    }
}
