package cao.cuong.supership.supership.ui.customer.user.login

import android.content.Context
import android.util.Log
import cao.cuong.supership.supership.data.model.AccessToken
import cao.cuong.supership.supership.data.model.Shipper
import cao.cuong.supership.supership.data.model.Staff
import cao.cuong.supership.supership.data.model.UserInfo
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.ShipperRepository
import cao.cuong.supership.supership.data.source.StaffRepository
import cao.cuong.supership.supership.data.source.UserRepository
import cao.cuong.supership.supership.data.source.remote.network.ApiClient
import cao.cuong.supership.supership.data.source.remote.network.ApiException
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.splash.splash.SplashFragment
import com.google.gson.Gson
import io.reactivex.Notification
import io.reactivex.subjects.PublishSubject

/**
 *
 * @author at-cuongcao.
 */
class LoginFragmentViewModel(private val context: Context) {

    private val localRepository = LocalRepository(context)
    private val userRepository = UserRepository()
    private val shipperRepository = ShipperRepository()
    private val staffRepository = StaffRepository()

    internal val loginStatusObserver = PublishSubject.create<Notification<AccessToken>>()
    internal val updateProgressStatus = PublishSubject.create<Boolean>()

    internal fun login(user: String, pass: String) {

        when (localRepository.getModule()) {
            SplashFragment.SHIPPER_MODULE -> {
                shipperRepository.login(user, pass)
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

            SplashFragment.CUSTOMER_MODULE -> {
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

            SplashFragment.STAFF_MODULE -> {
                staffRepository.login(user, pass)
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
        }
    }

    internal fun getModule() = localRepository.getModule()

    internal fun removeModule() = localRepository.chooseModule(-1)

    private fun saveAccessToken(userInfo: UserInfo) {
        if (userInfo.token.isNotEmpty()) {
            ApiClient.getInstance(null).token = userInfo.token
            localRepository.saveAccessToken(userInfo.token)
            localRepository.saveUserInfo(userInfo)
            loginStatusObserver.onNext(Notification.createOnNext(AccessToken(userInfo.token)))
        } else {
            loginStatusObserver.onError(ApiException(678, "Xãy ra lỗi! Vui lòng thử lại"))
        }
    }

    private fun saveAccessToken(shipper: Shipper) {
        if (shipper.token.isNotEmpty()) {
            ApiClient.getInstance(null).token = shipper.token
            localRepository.saveAccessToken(shipper.token)
            localRepository.saveShipperInfo(shipper)
            loginStatusObserver.onNext(Notification.createOnNext(AccessToken(shipper.token)))
        } else {
            loginStatusObserver.onError(ApiException(678, "Xãy ra lỗi! Vui lòng thử lại"))
        }
    }

    private fun saveAccessToken(staff: Staff) {
        if (staff.token.isNotEmpty()) {
            ApiClient.getInstance(null).token = staff.token
            localRepository.saveAccessToken(staff.token)
            Log.i("tag11", Gson().toJson(staff))
            localRepository.saveStaffInfo(staff)
            loginStatusObserver.onNext(Notification.createOnNext(AccessToken(staff.token)))
        } else {
            loginStatusObserver.onError(ApiException(678, "Xãy ra lỗi! Vui lòng thử lại"))
        }
    }
}
