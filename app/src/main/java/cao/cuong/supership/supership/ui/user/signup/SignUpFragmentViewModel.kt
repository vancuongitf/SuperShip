package cao.cuong.supership.supership.ui.user.signup

import cao.cuong.supership.supership.data.source.UserRepository
import cao.cuong.supership.supership.data.source.remote.request.CreateUserBody

/**
 *
 * @author at-cuongcao.
 */
class SignUpFragmentViewModel {

    private val userRepository = UserRepository()

    internal fun createUser(user: CreateUserBody) = userRepository.createUser(user)
}
