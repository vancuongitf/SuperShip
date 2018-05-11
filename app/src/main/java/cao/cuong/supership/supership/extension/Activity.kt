package cao.cuong.supership.supership.extension

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.source.remote.network.ApiException
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.base.BaseFragment
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.okButton


/**
 *
 * @author at-cuongcao.
 */
internal fun FragmentActivity.replaceFragment(@IdRes containerId: Int, fragment: Fragment,
                                              isAddBackStack: Boolean = false) {
    if (supportFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(containerId, fragment, fragment.javaClass.simpleName)
        if (isAddBackStack) {
            transaction.addToBackStack(fragment.javaClass.simpleName)
        }
        transaction.commit()
    }
}

internal fun FragmentActivity.addFragment(@IdRes containerId: Int, fragment: BaseFragment,
                                          t: (transaction: FragmentTransaction) -> Unit = {}, backStackString: String? = null) {
    if (supportFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null) {
        val transaction = supportFragmentManager.beginTransaction()
        t.invoke(transaction)
        transaction.add(containerId, fragment, fragment.javaClass.simpleName)
        if (backStackString != null) {
            transaction.addToBackStack(backStackString)
        }
        transaction.commit()
        supportFragmentManager.executePendingTransactions()
    }
}

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

internal fun Context.permissionIsEnable(permission: String) = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

internal fun Context.showOkAlert(@StringRes title: Int, message: String, okOnClicked: () -> Unit = {}) {
    this.alert {
        this.titleResource = title
        this.message = message
        isCancelable = false

        okButton {
            it.dismiss()
            okOnClicked()
        }
    }.show()
}

internal fun Context.showOkAlert(@StringRes title: Int, @StringRes message: Int, okOnClicked: () -> Unit = {}) {
    this.alert {
        this.titleResource = title
        this.messageResource = message
        isCancelable = false

        okButton {
            it.dismiss()
            okOnClicked()
        }
    }.show()
}

internal fun Context.showOkAlert(throwable: Throwable, okOnClicked: () -> Unit = {}) {
    this.alert {
        this.titleResource = R.string.notification
        if (throwable is ApiException) {
            this.message = throwable.messageError
        } else {
            this.message = throwable.message?:"Xãy ra lỗi! Vui lòng thử lại sau"
        }
        isCancelable = false
        okButton {
            it.dismiss()
            okOnClicked()
        }
    }.show()
}

internal fun Context.showConfirmAlert(@StringRes message: Int, okButtonClick: () -> Unit) {
    alert {
        titleResource = R.string.notification
        messageResource = message

        okButton {
            okButtonClick()
            it.dismiss()
        }

        cancelButton {
            it.dismiss()
        }
    }.show()
}

internal fun BaseActivity.hideKeyBoard() {
    val view = currentFocus
    if (view != null) {
        val imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
