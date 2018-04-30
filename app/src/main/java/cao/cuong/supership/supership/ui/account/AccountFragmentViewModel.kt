package cao.cuong.supership.supership.ui.account

import android.content.Context
import cao.cuong.supership.supership.data.model.RxEvent.UpdateAccountUI
import cao.cuong.supership.supership.data.model.UserInfo
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.UserRepository
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import io.reactivex.Single

/**
 *
 * @author at-cuongcao.
 */
class AccountFragmentViewModel(private val context: Context) {

    private val localRepository = LocalRepository(context)
    private val userRepository = UserRepository()

    internal fun isLogin(): Boolean {
        if (localRepository.getAccessToken().isNotEmpty()) {
            return true
        }
        return false
    }

    internal fun getUserInfo() = userRepository.getUserInfo(localRepository.getAccessToken())

    internal fun logOut() {
        localRepository.clearAccessToken()
        RxBus.publish(UpdateAccountUI())
    }
}
