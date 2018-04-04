package cao.cuong.supership.supership.ui.home.search

import cao.cuong.supership.supership.data.model.StoreInfoExpress
import cao.cuong.supership.supership.data.source.StoreRepository
import cao.cuong.supership.supership.data.source.remote.response.StoreExpressResponse
import io.reactivex.Notification
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

/**
 *
 * @author at-cuongcao.
 */
class SearchDialogViewModel {

    internal val stores = mutableListOf<StoreInfoExpress>()
    internal val updateListObservable = PublishSubject.create<Notification<Boolean>>()
    private val storeRepository = StoreRepository()
    private var currentPage = 1
    private val searchObservable = PublishSubject.create<String>()

    init {
        initSearchObservable()
    }

    internal fun search(query: String) {
        currentPage = 1
        searchObservable.onNext(query)
    }

    internal fun loadMore(query: String) {
        currentPage++
        searchObservable.onNext(query)
    }

    private fun initSearchObservable() {
        searchObservable
                .observeOn(Schedulers.computation())
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .flatMap {
                    if (it.isBlank()) {
                        loadSearchHistory()
                    } else {
                        callSearchApi(it, currentPage)
                    }
                }.subscribe({
                    if (it.isOnError) {
                        updateListObservable.onNext(Notification.createOnError(it.error
                                ?: Throwable()))
                    } else {

                        updateListObservable.onNext(Notification.createOnNext(true))
                    }
                })
    }

    private fun loadSearchHistory(): PublishSubject<Notification<List<StoreInfoExpress>>> {
        val result = PublishSubject.create<Notification<List<StoreInfoExpress>>>()
        thread {
            try {
                Thread.sleep(500)
                result.onNext(Notification.createOnNext(mutableListOf()))
            } catch (e: Exception) {

            }
        }
        return result
    }

    private fun callSearchApi(query: String, page: Int): PublishSubject<Notification<StoreExpressResponse>> {
        val result = PublishSubject.create<Notification<StoreExpressResponse>>()
        storeRepository.searchStore(query, page)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    result.onNext(Notification.createOnNext(it))
                }, {
                    result.onNext(Notification.createOnError(it))
                })
        return result
    }
}
