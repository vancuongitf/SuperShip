package cao.cuong.supership.supership.ui.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.util.Patterns
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.extension.isGPSEnable
import cao.cuong.supership.supership.extension.isNetworkConnection
import cao.cuong.supership.supership.extension.permissionIsEnable
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.main.MainActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.yesButton
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


/**
 *
 * @author at-cuongcao.
 */
class SplashActivity : BaseActivity() {

    companion object {
        private const val REQUEST_CODE_TURN_ON_GSP = 22
        private const val REQUEST_CODE_TURN_ON_NETWORK = 2
        private const val REQUEST_CODE_REQUEST_PERMISSION = 222
    }

    private lateinit var ui: SplashActivityUI
    private lateinit var viewModel: SplashActivityViewModel
    private var discardNetwork = false
    private var discardGps = false
    private var dialogRequestPermissionIsShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = SplashActivityUI()
        ui.setContentView(this)
        viewModel = SplashActivityViewModel(LocalRepository(this))
        Observable.interval(0, 5000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    ui.imgShipper.startAnimation(inFromLeftAnimation())
                })
        if (!permissionIsEnable(Manifest.permission.ACCESS_FINE_LOCATION)) {
            dialogRequestPermissionIsShowing = true
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_REQUEST_PERMISSION)
        }
    }

    override fun onBindViewModel() {
        if (dialogRequestPermissionIsShowing) {
            return
        }
        if (!discardGps && permissionIsEnable(Manifest.permission.ACCESS_FINE_LOCATION)) {
            checkGpsStatus()
            return
        }
        if (!discardNetwork) {
            checkNetworkStatus()
            return
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
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
        when (requestCode) {
            REQUEST_CODE_REQUEST_PERMISSION -> {
                dialogRequestPermissionIsShowing = false
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onBindViewModel()
                } else {
                    onBindViewModel()
                }
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
        if (!isGPSEnable()) {
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
        if (!isNetworkConnection()) {
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
