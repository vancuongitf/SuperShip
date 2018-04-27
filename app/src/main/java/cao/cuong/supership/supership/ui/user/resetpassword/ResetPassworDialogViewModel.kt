package cao.cuong.supership.supership.ui.user.resetpassword

import cao.cuong.supership.supership.data.source.UserRepository

/**
 *
 * @author at-cuongcao.
 */
class ResetPassworDialogViewModel {

    private val userRepository = UserRepository()

    internal fun resetPassword(userId: Int, pass: String, otpCode: Int) = userRepository.resetPassword(userId, pass, otpCode)
}
