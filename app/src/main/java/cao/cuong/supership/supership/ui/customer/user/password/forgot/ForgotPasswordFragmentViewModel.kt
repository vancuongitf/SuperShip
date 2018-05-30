package cao.cuong.supership.supership.ui.customer.user.password.forgot

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.ShipperRepository
import cao.cuong.supership.supership.data.source.UserRepository
import cao.cuong.supership.supership.ui.splash.splash.SplashFragment

/**
 *
 * @author at-cuongcao.
 */
class ForgotPasswordFragmentViewModel(context: Context) {
    private val localRepository = LocalRepository(context)
    private val userRepository = UserRepository()
    private val shipperRepository = ShipperRepository()

    internal fun requestResetPassword(email: String) = when (localRepository.getModule()) {
        SplashFragment.SHIPPER_MODULE -> shipperRepository.requestResetPassword(email)

        SplashFragment.CUSTOMER_MODULE -> userRepository.requestResetPassword(email)

        else -> userRepository.requestResetPassword(email)
    }
}
