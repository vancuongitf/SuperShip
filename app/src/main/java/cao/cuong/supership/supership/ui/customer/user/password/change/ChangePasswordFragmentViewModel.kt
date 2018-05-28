package cao.cuong.supership.supership.ui.customer.user.password.change

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.ShipperRepository
import cao.cuong.supership.supership.data.source.UserRepository
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.splash.splash.SplashFragment
import io.reactivex.subjects.BehaviorSubject

class ChangePasswordFragmentViewModel(context: Context) {

    internal val progressDialogStatusObservable = BehaviorSubject.create<Boolean>()

    private val localRepository = LocalRepository(context)
    private val userRepository = UserRepository()
    private val shipperRepository = ShipperRepository()

    internal fun changePassword(oldPassword: String, newPassword: String) = when (localRepository.getModule()) {
        SplashFragment.CUSTOMER_MODULE -> userRepository
                .changePassword(oldPassword, newPassword, localRepository.getAccessToken())
                .observeOnUiThread()
                .doOnSubscribe {
                    progressDialogStatusObservable.onNext(true)
                }
                .doFinally {
                    progressDialogStatusObservable.onNext(false)
                }

        SplashFragment.SHIPPER_MODULE -> shipperRepository
                .changeShipperPassword(localRepository.getShipperInfo()?.id
                        ?: -1, oldPassword, newPassword)
                .observeOnUiThread()
                .doOnSubscribe {
                    progressDialogStatusObservable.onNext(true)
                }
                .doFinally {
                    progressDialogStatusObservable.onNext(false)
                }

        else -> userRepository
                .changePassword(oldPassword, newPassword, localRepository.getAccessToken())
                .observeOnUiThread()
                .doOnSubscribe {
                    progressDialogStatusObservable.onNext(true)
                }
                .doFinally {
                    progressDialogStatusObservable.onNext(false)
                }
    }
}
