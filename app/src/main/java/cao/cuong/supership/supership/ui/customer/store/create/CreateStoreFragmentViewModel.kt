package cao.cuong.supership.supership.ui.customer.store.create

import android.content.Context
import android.net.Uri
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StoreRepository
import cao.cuong.supership.supership.data.source.remote.request.CreateStoreBody
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.subjects.BehaviorSubject
import java.io.File

class CreateStoreFragmentViewModel(context: Context) {

    internal val progressDialogStatusObservable = BehaviorSubject.create<Boolean>()
    private val storeRepository = StoreRepository()
    private val localRepository = LocalRepository(context)

    internal fun createStore(uri: Uri, createStoreBody: CreateStoreBody) = storeRepository.uploadImage(File(uri.path))
            .observeOnUiThread()
            .flatMap {
                createStoreBody.token = localRepository.getAccessToken()
                createStoreBody.image = it.message
                storeRepository.createStore(createStoreBody)
                        .observeOnUiThread()
            }
            .doOnSubscribe {
                progressDialogStatusObservable.onNext(true)
            }
            .doFinally {
                progressDialogStatusObservable.onNext(false)
            }
}
