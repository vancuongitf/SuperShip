package cao.cuong.supership.supership.extension

import com.google.android.gms.maps.model.LatLng

/**
 *
 * @author at-cuongcao.
 */
internal fun LatLng.distanceTo(latLng: LatLng): Long {
    val earthRadius = 6371000 // Radius of the earth in km
    val rLat = degreeToRadian(latitude - latLng.latitude)
    val rLng = degreeToRadian(longitude - latLng.longitude)
    val a = Math.sin(rLat / 2) * Math.sin(rLat / 2) + Math.cos(degreeToRadian(latitude)) * Math.cos(degreeToRadian(latLng.latitude)) * Math.sin(rLng / 2) * Math.sin(rLng / 2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    return Math.ceil(earthRadius * c).toLong()
}

private fun degreeToRadian(degree: Double) = degree * Math.PI / 180
