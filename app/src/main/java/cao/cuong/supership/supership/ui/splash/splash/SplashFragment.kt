package cao.cuong.supership.supership.ui.splash.splash

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.isGPSEnable
import cao.cuong.supership.supership.extension.isNetworkConnection
import cao.cuong.supership.supership.extension.permissionIsEnable
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.main.MainActivity
import cao.cuong.supership.supership.ui.shipper.main.ShipperMainActivity
import cao.cuong.supership.supership.ui.splash.SplashActivity
import cao.cuong.supership.supership.ui.staff.main.StaffMainActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton
import java.util.concurrent.TimeUnit

class SplashFragment : BaseFragment() {

    companion object {
        private const val REQUEST_CODE_TURN_ON_GSP = 22
        private const val REQUEST_CODE_TURN_ON_NETWORK = 2
        internal const val REQUEST_CODE_REQUEST_PERMISSION = 222
        internal const val CUSTOMER_MODULE = 0
        internal const val SHIPPER_MODULE = 1
        internal const val STAFF_MODULE = 2
    }

    private lateinit var ui: SplashFragmentUI
    private lateinit var viewModel: SplashFragmentViewModel
    private var discardNetwork = false
    private var discardGps = false
    internal var dialogRequestPermissionIsShowing = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = SplashFragmentViewModel(context)
        ui = SplashFragmentUI()
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Observable.interval(0, 5000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    ui.imgShipper.startAnimation(inFromLeftAnimation())
                })
        if (!context.permissionIsEnable(Manifest.permission.ACCESS_FINE_LOCATION)) {
            dialogRequestPermissionIsShowing = true
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_REQUEST_PERMISSION)
        }
    }

    override fun onBindViewModel() {
        if (dialogRequestPermissionIsShowing) {
            return
        }

        if (!discardGps && context.permissionIsEnable(Manifest.permission.ACCESS_FINE_LOCATION)) {
            checkGpsStatus()
            return
        }

        if (!discardNetwork) {
            checkNetworkStatus()
            return
        }

        when (viewModel.getModule()) {
            CUSTOMER_MODULE -> {
                startActivity(Intent(context, MainActivity::class.java))
                activity.finish()
            }

            SHIPPER_MODULE -> {
                if (viewModel.isLogin()) {
                    startActivity(Intent(context, ShipperMainActivity::class.java))
                    activity.finish()
                } else {
                    (activity as? BaseActivity)?.startUserActivity()
                }
            }

            STAFF_MODULE -> {
                startActivity(Intent(context, StaffMainActivity::class.java))
                activity.finish()
            }

            else -> {
                (activity as? SplashActivity)?.openChooseModuleFragment()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_TURN_ON_GSP -> {
                discardGps = true
            }

            REQUEST_CODE_TURN_ON_NETWORK -> {

            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Log.i("tag11","xxxx")
        when (requestCode) {
            REQUEST_CODE_REQUEST_PERMISSION -> {
                dialogRequestPermissionIsShowing = false
                onBindViewModel()
            }
        }
    }

    private fun inFromLeftAnimation(): Animation {
        val inFromLeft = TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -0.5f,
                Animation.RELATIVE_TO_PARENT, 1.5f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f)
        inFromLeft.duration = 5000
        inFromLeft.interpolator = AccelerateInterpolator()
        return inFromLeft
    }

    private fun checkGpsStatus() {
        if (!context.isGPSEnable()) {
            alert {
                title = getString(R.string.notification_string)
                messageResource = R.string.turn_on_gps_message
                isCancelable = false
                yesButton {
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivityForResult(intent, REQUEST_CODE_TURN_ON_GSP)
                }

                noButton {
                    discardGps = true
                    onBindViewModel()
                }
            }.show()
        } else {
            discardGps = true
            onBindViewModel()
        }
    }

    private fun checkNetworkStatus() {
        if (!context.isNetworkConnection()) {
            alert {
                title = getString(R.string.notification_string)
                messageResource = R.string.turn_on_network_message
                isCancelable = false
                discardNetwork = true
                yesButton {
                    val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivityForResult(intent, REQUEST_CODE_TURN_ON_NETWORK)
                }

                noButton {
                    onBindViewModel()
                }
            }.show()
        } else {
            discardNetwork = true
            onBindViewModel()
        }
    }
}
