package cao.cuong.supership.supership.ui.user.password.change

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.UserRepository
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.subjects.BehaviorSubject

class ChangePasswordDialogViewModel(context: Context) {

    internal val progressDialogStatusObservable = BehaviorSubject.create<Boolean>()

    private val localRepository = LocalRepository(context)
    private val userRepository = UserRepository()

    internal fun changePassword(oldPassword: String, newPassword: String) = userRepository
            .changePassword(oldPassword, newPassword, localRepository.getAccessToken())
            .observeOnUiThread()
            .doOnSubscribe {
                progressDialogStatusObservable.onNext(true)
            }
            .doFinally {
                progressDialogStatusObservable.onNext(false)
            }
}
