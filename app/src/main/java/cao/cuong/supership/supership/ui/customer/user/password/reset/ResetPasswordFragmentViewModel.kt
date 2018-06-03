package cao.cuong.supership.supership.ui.customer.user.password.reset

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.ShipperRepository
import cao.cuong.supership.supership.data.source.StaffRepository
import cao.cuong.supership.supership.data.source.UserRepository
import cao.cuong.supership.supership.ui.splash.splash.SplashFragment

/**
 *
 * @author at-cuongcao.
 */
class ResetPasswordFragmentViewModel(context: Context) {

    private val localRepository = LocalRepository(context)
    private val userRepository = UserRepository()
    private val shipperRepository = ShipperRepository()
    private val staffRepository = StaffRepository()

    internal fun resetPassword(userId: Long, pass: String, otpCode: Int) = when (localRepository.getModule()) {
        SplashFragment.CUSTOMER_MODULE -> {
            userRepository.resetPassword(userId, pass, otpCode)
        }

        SplashFragment.SHIPPER_MODULE -> {
            shipperRepository.resetShipperPassword(userId, pass, otpCode)
        }

        else -> {
            staffRepository.resetPassword(userId, pass, otpCode)
        }
    }
}
