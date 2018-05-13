package cao.cuong.supership.supership.ui.customer.user.password.forgot

import cao.cuong.supership.supership.data.source.UserRepository

/**
 *
 * @author at-cuongcao.
 */
class ForgotPasswordFragmentViewModel {
    private val userRepository = UserRepository()

    internal fun requestResetPassword(email: String) = userRepository.requestResetPassword(email)
}
