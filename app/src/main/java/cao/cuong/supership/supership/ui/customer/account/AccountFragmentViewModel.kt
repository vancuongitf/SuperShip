package cao.cuong.supership.supership.ui.customer.account

import android.content.Context
import cao.cuong.supership.supership.data.model.rxevent.UpdateAccountUI
import cao.cuong.supership.supership.data.model.UserInfo
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.UserRepository
import cao.cuong.supership.supership.data.source.remote.network.RxBus

/**
 *
 * @author at-cuongcao.
 */
class AccountFragmentViewModel(context: Context) {

    private val localRepository = LocalRepository(context)
    private val userRepository = UserRepository()

    internal fun isLogin() = localRepository.getAccessToken().isNotEmpty()

    internal fun getUserInfo() = userRepository.getUserInfo(localRepository.getAccessToken())

    internal fun getLocalUserInfo() = localRepository.getUserInfo()

    internal fun saveUserInfo(userInfo: UserInfo) = localRepository.saveUserInfo(userInfo)

    internal fun logOut() {
        localRepository.clearAccessToken()
        RxBus.publish(UpdateAccountUI())
    }
}
