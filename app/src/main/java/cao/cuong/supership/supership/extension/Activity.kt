package cao.cuong.supership.supership.extension

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.view.WindowManager
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject


/**
 *
 * @author at-cuongcao.
 */
internal fun Context.isNetworkConnection(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    return cm?.activeNetworkInfo != null
}

internal fun Context.isGPSEnable(): Boolean {
    val manager = getSystemService(Context.LOCATION_SERVICE) as? LocationManager
    return manager != null && manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

internal fun Context.getWidthScreen(): Int {
    val wm = getSystemService(Context.WINDOW_SERVICE) as? WindowManager
    val dimension = DisplayMetrics()
    wm?.defaultDisplay?.getMetrics(dimension)
    return dimension.widthPixels
}

internal fun Context.getHeightScreen(): Int {
    val wm = getSystemService(Context.WINDOW_SERVICE) as? WindowManager
    val dimension = DisplayMetrics()
    wm?.defaultDisplay?.getMetrics(dimension)
    return dimension.heightPixels
}

internal fun Context.getLastKnowLocation(): Single<LatLng> {
    val result = SingleSubject.create<LatLng>()
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            result.onError(Throwable())
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    result.onSuccess(LatLng(it.latitude, it.longitude))
                } else {
                    result.onError(Throwable())
                }
            }
        }
    } else {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                result.onSuccess(LatLng(it.latitude, it.longitude))
            } else {
                result.onError(Throwable())
            }
        }
    }
    return result
}

internal fun Context.permissionIsEnable(permission: String): Boolean =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
