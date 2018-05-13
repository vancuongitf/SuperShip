package cao.cuong.supership.supership.ui.customer.bill.info

import android.content.Context
import cao.cuong.supership.supership.data.model.paypal.VerifyPaymentBody
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.UserRepository
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.subjects.BehaviorSubject

class BillInfoFragmentViewModel(context: Context) {

    internal val updateProgressStatusObservable = BehaviorSubject.create<Boolean>()

    private val localRepository = LocalRepository(context)
    private val userRepository = UserRepository()

    internal fun getBillInfo(id: Long) = userRepository
            .getBillInfo(localRepository.getAccessToken(), id)
            .observeOnUiThread()
            .doOnSubscribe {
                updateProgressStatusObservable.onNext(true)
            }
            .doFinally {
                updateProgressStatusObservable.onNext(false)
            }

    internal fun verifyPayment(userId: Long, billId: Long, payId: String) = userRepository
            .verifyPayment(VerifyPaymentBody(userId, billId, payId))
            .observeOnUiThread()
            .doOnSubscribe {
                updateProgressStatusObservable.onNext(true)
            }
            .doFinally {
                updateProgressStatusObservable.onNext(false)
            }

    internal fun getUserId() = localRepository.getUserInfo()?.id
}
