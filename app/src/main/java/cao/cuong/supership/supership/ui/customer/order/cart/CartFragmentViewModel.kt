package cao.cuong.supership.supership.ui.customer.order.cart

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StoreRepository
import cao.cuong.supership.supership.data.source.remote.request.BillBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

class CartFragmentViewModel(context: Context) {

    internal val progressDialogStatusObservable = BehaviorSubject.create<Boolean>()
    private val localRepository = LocalRepository(context)
    private val storeRepository = StoreRepository()

    internal fun submitOrder(billBody: BillBody): Single<MessageResponse> {
        billBody.userId = localRepository.getUserInfo()?.id ?: -1
        return storeRepository.orderDrink(billBody)
                .observeOnUiThread()
                .doOnSubscribe {
                    progressDialogStatusObservable.onNext(true)
                }
                .doFinally {
                    progressDialogStatusObservable.onNext(false)
                }
    }

    fun getUserInfo() = localRepository.getUserInfo()

    internal fun isLogin() = localRepository.isLogin()
}
