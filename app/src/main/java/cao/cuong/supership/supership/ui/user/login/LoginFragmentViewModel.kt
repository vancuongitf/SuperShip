package cao.cuong.supership.supership.ui.user.login

import android.content.Context
import cao.cuong.supership.supership.data.model.AccessToken
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.UserRepository
import cao.cuong.supership.supership.data.source.remote.network.ApiException
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.Notification
import io.reactivex.subjects.PublishSubject

/**
 *
 * @author at-cuongcao.
 */
class LoginFragmentViewModel(private val context: Context) {

    private val localRepository = LocalRepository(context)
    private val userRepository = UserRepository()

    internal val loginStatusObserver = PublishSubject.create<Notification<AccessToken>>()
    internal val updateProgressStatus = PublishSubject.create<Boolean>()

    internal fun login(user: String, pass: String) {
        userRepository.login(user, pass)
                .observeOnUiThread()
                .doOnSubscribe({
                    updateProgressStatus.onNext(true)
                })
                .doFinally {
                    updateProgressStatus.onNext(false)
                }
                .subscribe(this::saveAccessToken, {
                    loginStatusObserver.onNext(Notification.createOnError(it))
                })
    }

    internal fun saveAccessToken(token: AccessToken) {
        if (token.token.isNotEmpty()) {
            localRepository.saveAccessToken(token.token)
            loginStatusObserver.onNext(Notification.createOnNext(token))
        } else {
            loginStatusObserver.onError(ApiException(678, "Xãy ra lỗi! Vui lòng thử lại"))
        }
    }
}
