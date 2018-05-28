package cao.cuong.supership.supership.ui.customer.home.search

import android.content.Context
import cao.cuong.supership.supership.data.model.StoreInfoExpress
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StoreRepository
import cao.cuong.supership.supership.data.source.remote.network.CustomCall
import cao.cuong.supership.supership.data.source.remote.network.CustomCallback
import cao.cuong.supership.supership.data.source.remote.response.StoreExpressResponse
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.Notification
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.TimeUnit

/**
 *
 * @author at-cuongcao.
 */
class SearchDialogViewModel(private val context: Context) {

    internal val stores = mutableListOf<StoreInfoExpress>()
    internal val updateListObservable = PublishSubject.create<Notification<Boolean>>()
    internal var currentQuery = ""
    private val storeRepository = StoreRepository()
    private val localRepository = LocalRepository(context)
    private var currentPage = 1
    private val searchObservable = PublishSubject.create<String>()
    private var currentCall: CustomCall<StoreExpressResponse>? = null

    init {
        initSearchObservable()
    }

    internal fun search(query: String) {
        currentPage = 1
        searchObservable.onNext(query)
    }

    internal fun loadMore() {
        currentPage++
        callSearchApi(currentQuery)
    }

    internal fun saveHistory(store:StoreInfoExpress){
        localRepository.saveSearchHistory(store)
    }

    private fun initSearchObservable() {
        searchObservable
                .observeOn(Schedulers.computation())
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .flatMap {
                    if (it.isBlank()) {
                        currentCall?.cancel()
                        loadSearchHistory()
                    } else {
                        currentQuery = it
                        callSearchApi(it)
                    }
                }.subscribe({
                    updateListObservable.onNext(it)
                }, {
                    updateListObservable.onNext(Notification.createOnError(it))
                })
    }

    private fun loadSearchHistory(): PublishSubject<Notification<Boolean>> {
        val result = PublishSubject.create<Notification<Boolean>>()
        localRepository.getSearchHistory()
                .observeOnUiThread()
                .subscribe({
                    stores.clear()
                    stores.addAll(it.storeList)
                    result.onNext(Notification.createOnNext(false))
                }, {
                    result.onError(it)
                })
        return result
    }

    private fun callSearchApi(query: String): PublishSubject<Notification<Boolean>> {
        if (currentCall != null) {
            currentCall?.cancel()
        }
        val result = PublishSubject.create<Notification<Boolean>>()
        currentCall = storeRepository.searchStore(query, currentPage)
        currentCall?.enqueue(object : CustomCallback<StoreExpressResponse> {
            override fun success(call: Call<StoreExpressResponse>, response: Response<StoreExpressResponse>) {
                val data = response.body()
                if (data != null) {
                    if (currentPage == 1) {
                        stores.clear()
                        stores.addAll(data.storeList)
                    } else {
                        data.storeList.forEach {
                            val newStore = it
                            if (!stores.any {
                                        it.storeId == newStore.storeId
                                    }) {
                                stores.add(newStore)
                            }
                        }
                    }
                    result.onNext(Notification.createOnNext(data.nextPageFlag))
                } else {
                    result.onNext(Notification.createOnError(Throwable()))
                }
            }

            override fun onError(t: Throwable) {
                result.onNext(Notification.createOnError(t))
            }

        })
        return result
    }
}
