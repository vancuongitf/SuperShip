package cao.cuong.supership.supership.ui.home.store

import android.Manifest
import android.content.Context
import android.util.Log
import cao.cuong.supership.supership.data.model.StoreInfoExpress
import cao.cuong.supership.supership.data.source.StoreRepository
import cao.cuong.supership.supership.extension.getLastKnowLocation
import cao.cuong.supership.supership.extension.isGPSEnable
import cao.cuong.supership.supership.extension.isNetworkConnection
import cao.cuong.supership.supership.extension.permissionIsEnable
import com.google.gson.Gson
import io.reactivex.Notification
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

/**
 *
 * @author at-cuongcao.
 */
class StoreFragmentViewModel(private val context: Context, private val advanceParam: Int) {

    internal var currentPage = 1
    internal val getExpressesStoreObservable = PublishSubject.create<Notification<Boolean>>()
    internal val stores = mutableListOf<StoreInfoExpress>()
    private val storeRepository = StoreRepository()
    private var waitingTimeForLocation = 20000L

    internal fun getExpressesStore() {
        if (context.permissionIsEnable(Manifest.permission.ACCESS_FINE_LOCATION) && context.isGPSEnable() && context.isNetworkConnection()) {
            context.getLastKnowLocation()
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        Log.i("tag11", "aaaa")
                        getExpressStore(it.latitude, it.longitude)
                    }, {
                        Log.i("tag11", "bbbb")
                        getExpressStore(null, null)
                    })
        } else {
            getExpressStore(null, null)
        }
    }

    internal fun loadMore(lat: Double?, lng: Double?) {
        currentPage++
        getExpressesStore()
    }

    private fun getExpressStore(lat: Double?, lng: Double?) {
        storeRepository.getStoreExpressList(advanceParam, currentPage, lat, lng)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.i("tag11", Gson().toJson(it.storeList))
                    if (currentPage == 1) {
                        stores.clear()
                        stores.addAll(it.storeList)
                    } else {
                        stores.addAll(it.storeList)
                    }
                    getExpressesStoreObservable.onNext(Notification.createOnNext(it.nextPageFlag))
                }, {
                    Log.i("tag11", Gson().toJson(it))
                    getExpressesStoreObservable.onNext(Notification.createOnError(it))
                })
    }
}
