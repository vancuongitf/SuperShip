package cao.cuong.supership.supership.ui.shipper.bill.checked

import android.content.Context
import cao.cuong.supership.supership.data.model.ExpressBill
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.ShipperRepository
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.Notification
import io.reactivex.subjects.BehaviorSubject

class CheckedBillFragmentViewModel(context: Context) {

    internal val updateListObservable = BehaviorSubject.create<Notification<Boolean>>()
    internal val checkedBills = mutableListOf<ExpressBill>()

    private val localRepository = LocalRepository(context)
    private val shipperRepository = ShipperRepository()
    private var currentPage = 1
    private var nextPageFlag = false

    internal fun getCheckedBills() {
        shipperRepository.getCheckedBills(localRepository.getAccessToken(), currentPage)
                .observeOnUiThread()
                .subscribe({
                    if (currentPage == 1) {
                        checkedBills.clear()
                    }
                    nextPageFlag = it.nextPageFlag
                    it.orders.forEach {
                        val id = it.id
                        if (!checkedBills.any {
                                    it.id == id
                                }) {
                            checkedBills.add(it)
                        }
                    }
                    updateListObservable.onNext(Notification.createOnNext(true))
                }, {
                    currentPage--
                    updateListObservable.onNext(Notification.createOnError(it))
                })
    }

    internal fun isLogin() = localRepository.getAccessToken().isNotEmpty()
}
