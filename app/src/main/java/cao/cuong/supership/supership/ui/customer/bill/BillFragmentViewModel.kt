package cao.cuong.supership.supership.ui.customer.bill

import android.content.Context
import cao.cuong.supership.supership.data.model.ExpressBill
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.UserRepository
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.Notification
import io.reactivex.subjects.PublishSubject

class BillFragmentViewModel(context: Context) {

    internal val notification = PublishSubject.create<Notification<Boolean>>()
    internal val bills = mutableListOf<ExpressBill>()

    private val localRepository = LocalRepository(context)
    private val userRepository = UserRepository()
    private var currentPage = 1
    private var nextPageFlag = false

    internal fun isLogin() = localRepository.isLogin()

    internal fun getOrders() {
        if (isLogin()) {
            userRepository.getOrders(localRepository.getAccessToken(), currentPage)
                    .observeOnUiThread()
                    .subscribe({
                        if (currentPage == 1) {
                            bills.clear()
                        }
                        nextPageFlag = it.nextPageFlag
                        bills.addAll(it.orders)
                        notification.onNext(Notification.createOnNext(true))
                    }, {
                        notification.onNext(Notification.createOnError(it))
                    })
        }
    }

}
