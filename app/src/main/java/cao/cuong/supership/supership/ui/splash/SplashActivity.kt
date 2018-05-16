package cao.cuong.supership.supership.ui.splash

import android.os.Bundle
import android.util.Log
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.replaceFragment
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.splash.module.ChooseModuleFragment
import cao.cuong.supership.supership.ui.splash.splash.SplashFragment
import org.jetbrains.anko.setContentView

/**
 *
 * @author at-cuongcao.
 */
class SplashActivity : BaseActivity() {

    private lateinit var ui: SplashActivityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = SplashActivityUI()
        ui.setContentView(this)
        replaceFragment(R.id.splashActivityContainer, SplashFragment())
    }

    override fun onBindViewModel() {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            SplashFragment.REQUEST_CODE_REQUEST_PERMISSION -> {
                (supportFragmentManager.findFragmentById(R.id.splashActivityContainer) as? SplashFragment)?.let {
                    it.dialogRequestPermissionIsShowing = false
                    it.onBindViewModel()
                }
            }
        }
    }

    internal fun openChooseModuleFragment() {
        replaceFragment(R.id.splashActivityContainer, ChooseModuleFragment())
    }
}
