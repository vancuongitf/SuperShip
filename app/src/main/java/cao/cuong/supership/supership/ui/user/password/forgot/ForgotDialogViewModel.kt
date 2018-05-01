package cao.cuong.supership.supership.ui.user.password.forgot

import cao.cuong.supership.supership.data.source.UserRepository

/**
 *
 * @author at-cuongcao.
 */
class ForgotDialogViewModel {
    private val userRepository = UserRepository()

    internal fun requestResetPassword(email: String) = userRepository.requestResetPassword(email)
}