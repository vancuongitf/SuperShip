package cao.cuong.supership.supership.ui.customer.home.store

import android.Manifest
import android.content.Context
import android.util.Log
import cao.cuong.supership.supership.data.model.StoreInfoExpress
import cao.cuong.supership.supership.data.source.StoreRepository
import cao.cuong.supership.supership.data.source.remote.network.google.GoogleClient
import cao.cuong.supership.supership.extension.*
import com.google.gson.Gson
import io.reactivex.Notification
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 *
 * @author at-cuongcao.
 */
class StoreFragmentViewModel(private val context: Context, private val advanceParam: Int) {

    internal var currentPage = 1
    internal val getExpressesStoreObservable = PublishSubject.create<Notification<Boolean>>()
    internal val stores = mutableListOf<StoreInfoExpress>()
    private val storeRepository = StoreRepository()
    private var waitingTimeForLocation = 5000L

    internal fun getExpressesStore() {
        if (context.isNetworkConnection()) {
            if (context.permissionIsEnable(Manifest.permission.ACCESS_FINE_LOCATION) && context.isGPSEnable()) {
                context.getLastKnowLocation()
                        .subscribeOn(Schedulers.io())
                        .timeout(waitingTimeForLocation, TimeUnit.MILLISECONDS)
                        .subscribe({
                            getExpressStore(it.latitude, it.longitude)
                        }, {
                            getExpressStore(null, null)
                        })
            } else {
                getExpressStore(null, null)
            }
        } else {
            getExpressesStoreObservable.onNext(Notification.createOnError(Throwable("Network")))
        }
    }

    internal fun loadMore() {
        currentPage++
        getExpressesStore()
    }

    private fun getExpressStore(lat: Double?, lng: Double?) {
        storeRepository.getStoreExpressList(advanceParam, currentPage, lat, lng)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (currentPage == 1) {
                        stores.clear()
                        stores.addAll(it.storeList)
                    } else {
                        stores.addAll(it.storeList)
                    }
                    getExpressesStoreObservable.onNext(Notification.createOnNext(it.nextPageFlag))
                }, {
                    getExpressesStoreObservable.onNext(Notification.createOnError(it))
                })
    }
}
