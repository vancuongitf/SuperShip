package cao.cuong.supership.supership.ui.customer.store.list

import android.content.Context
import cao.cuong.supership.supership.data.model.StoreInfoExpress
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.UserRepository
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.Notification
import io.reactivex.subjects.PublishSubject

class StoreListFragmentViewModel(context: Context) {
    internal val stores = mutableListOf<StoreInfoExpress>()
    internal var nextPageFlag = false
    internal val observableUpdateList = PublishSubject.create<Notification<Boolean>>()
    internal val observableProgressStatus = PublishSubject.create<Boolean>()

    private val userRepository = UserRepository()
    private val localRepository = LocalRepository(context)
    private var currentPage = 1

    internal fun getStoreListOfUser() {
        userRepository.getStoreList(localRepository.getAccessToken(), currentPage)
                .observeOnUiThread()
                .doOnSubscribe {
                    observableProgressStatus.onNext(true)
                }
                .doFinally {
                    observableProgressStatus.onNext(false)
                }
                .subscribe({
                    nextPageFlag = it.nextPageFlag
                    if (currentPage == 1) {
                        stores.clear()
                        stores.addAll(it.storeList)
                    } else {
                        it.storeList.forEach {
                            val store = it
                            if (!stores.any { it.storeId == store.storeId }) {
                                stores.add(store)
                            }
                        }
                    }
                    observableUpdateList.onNext(Notification.createOnNext(true))
                }, {
                    observableUpdateList.onNext(Notification.createOnError(it))
                })
    }
}
