package cao.cuong.supership.supership.ui.customer.user.password.reset

import cao.cuong.supership.supership.data.source.UserRepository

/**
 *
 * @author at-cuongcao.
 */
class ResetPassworFragmentViewModel {

    private val userRepository = UserRepository()

    internal fun resetPassword(userId: Int, pass: String, otpCode: Int) = userRepository.resetPassword(userId, pass, otpCode)
}
