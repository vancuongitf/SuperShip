package cao.cuong.supership.supership.ui.customer.store.edit

import android.content.Context
import android.net.Uri
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StoreRepository
import cao.cuong.supership.supership.data.source.remote.request.EditStoreBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import java.io.File

class EditStoreInfoFragmentViewModel(context: Context) {

    internal val progressDialogStatusObservable = BehaviorSubject.create<Boolean>()
    private val storeRepository = StoreRepository()
    private val localRepository = LocalRepository(context)

    internal fun getAccessToken() = localRepository.getAccessToken()

    internal fun editStoreInfo(uri: Uri?, editStoreBody: EditStoreBody): Single<MessageResponse> {
        return if (uri != null) {
            storeRepository.uploadImage(File(uri.path))
                    .observeOnUiThread()
                    .flatMap {
                        editStoreBody.image = it.message
                        storeRepository.editStoreInfo(editStoreBody)
                                .observeOnUiThread()
                    }
                    .observeOnUiThread()
                    .doOnSubscribe {
                        progressDialogStatusObservable.onNext(true)
                    }
                    .doFinally {
                        progressDialogStatusObservable.onNext(false)
                    }
        } else {
            storeRepository.editStoreInfo(editStoreBody)
                    .observeOnUiThread()
                    .doOnSubscribe {
                        progressDialogStatusObservable.onNext(true)
                    }
                    .doFinally {
                        progressDialogStatusObservable.onNext(false)
                    }
        }
    }

}
