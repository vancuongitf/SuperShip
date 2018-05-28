package cao.cuong.supership.supership.ui.customer.account

import android.content.Context
import cao.cuong.supership.supership.data.model.UserInfo
import cao.cuong.supership.supership.data.model.rxevent.UpdateAccountUI
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.UserRepository
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.subjects.BehaviorSubject

/**
 *
 * @author at-cuongcao.
 */
class AccountFragmentViewModel(context: Context) {

    internal val updateProgressDialogStatus = BehaviorSubject.create<Boolean>()
    private val localRepository = LocalRepository(context)
    private val userRepository = UserRepository()

    internal fun isLogin() = localRepository.isLogin()

    internal fun getUserInfo() = userRepository.getUserInfo(localRepository.getAccessToken())

    internal fun updateInfo(userId: Long, name: String, phone: String) = userRepository
            .updateUserInfo(userId, name, phone)
            .observeOnUiThread()
            .doOnSubscribe {
                updateProgressDialogStatus.onNext(true)
            }
            .doFinally {
                updateProgressDialogStatus.onNext(false)
            }

    internal fun getLocalUserInfo() = localRepository.getUserInfo()

    internal fun saveUserInfo(userInfo: UserInfo) = localRepository.saveUserInfo(userInfo)

    internal fun logOut() {
        localRepository.clearAccessToken()
        RxBus.publish(UpdateAccountUI())
    }
}
