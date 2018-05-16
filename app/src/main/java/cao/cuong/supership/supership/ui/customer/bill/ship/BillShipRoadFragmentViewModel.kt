package cao.cuong.supership.supership.ui.customer.bill.ship

import android.content.Context
import cao.cuong.supership.supership.data.model.BillLocation
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StoreRepository
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.splash.splash.SplashFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class BillShipRoadFragmentViewModel(context: Context, private val billId: Long) {

    internal val updateBillLocationObservable = PublishSubject.create<BillLocation>()
    private val localRepository = LocalRepository(context)
    private val storeRepository = StoreRepository()

    init {
        Observable.interval(1, 60, TimeUnit.SECONDS)
                .subscribe({
                    if (localRepository.getModule() == SplashFragment.CUSTOMER_MODULE) {
                        getBillLastLocation()
                    }
                })
    }

    private fun getBillLastLocation() = storeRepository
            .getLastedBillLocation(localRepository.getAccessToken(), billId)
            .observeOnUiThread()
            .timeout(25, TimeUnit.SECONDS)
            .subscribe({
                it?.let {
                    updateBillLocationObservable.onNext(it)
                }
            }, {})
}