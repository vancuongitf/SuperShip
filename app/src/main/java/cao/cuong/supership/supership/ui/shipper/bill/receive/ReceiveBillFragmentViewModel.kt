package cao.cuong.supership.supership.ui.shipper.bill.receive

import android.content.Context
import cao.cuong.supership.supership.data.model.ExpressBill
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.ShipperRepository
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.Notification
import io.reactivex.subjects.BehaviorSubject

class ReceiveBillFragmentViewModel(context: Context) {

    internal val updateListObservable = BehaviorSubject.create<Notification<Boolean>>()
    internal val checkedBills = mutableListOf<ExpressBill>()

    private val localRepository = LocalRepository(context)
    private val shipperRepository = ShipperRepository()
    private var currentPage = 1
    private var nextPageFlag = false
    private var currentStatus = 2

    internal fun getCheckedBills() {
        shipperRepository.getTookBills(localRepository.getAccessToken(), currentPage, currentStatus)
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

    internal fun changeStatus(newStatus: Int) {
        currentStatus = newStatus
        checkedBills.clear()
        updateListObservable.onNext(Notification.createOnNext(true))
        getCheckedBills()
    }

    internal fun isLogin() = localRepository.isLogin()
}
