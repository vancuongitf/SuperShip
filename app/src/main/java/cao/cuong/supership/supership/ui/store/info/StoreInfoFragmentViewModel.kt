package cao.cuong.supership.supership.ui.store.info

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StoreRepository
import cao.cuong.supership.supership.data.source.remote.request.UpdateStoreStatusBody
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.subjects.BehaviorSubject

class StoreInfoFragmentViewModel(context: Context, private val storeId: Long) {

    internal val progressDialogStatusObservable = BehaviorSubject.create<Boolean>()
    private val localRepository = LocalRepository(context)
    private val storeRepository = StoreRepository()

    internal fun getStoreInfo() = storeRepository.getStoreInfo(storeId)
            .observeOnUiThread()
            .doOnSubscribe {
                progressDialogStatusObservable.onNext(true)
            }
            .doFinally {
                progressDialogStatusObservable.onNext(false)
            }

    internal fun changeStoreStatus(updateStoreStatusBody: UpdateStoreStatusBody) = storeRepository.changeStoreStatus(updateStoreStatusBody)
            .observeOnUiThread()
            .doOnSubscribe {
                progressDialogStatusObservable.onNext(true)
            }
            .doFinally {
                progressDialogStatusObservable.onNext(false)
            }

    internal fun getAccessToken() = localRepository.getAccessToken()

}
