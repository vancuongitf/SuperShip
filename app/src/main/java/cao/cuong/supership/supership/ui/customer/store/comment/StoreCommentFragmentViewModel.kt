package cao.cuong.supership.supership.ui.customer.store.comment

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StoreRepository
import cao.cuong.supership.supership.data.source.remote.network.CustomCall
import cao.cuong.supership.supership.data.source.remote.network.CustomCallback
import cao.cuong.supership.supership.data.source.remote.response.StoreCommentResponse
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.Notification
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Response

class StoreCommentFragmentViewModel(context: Context) {

    internal val storeComment = StoreCommentResponse(false, mutableListOf(), mutableListOf())
    internal val updateProgressStatusObservable = BehaviorSubject.create<Boolean>()
    internal val getStoreCommentObservable = PublishSubject.create<Notification<Boolean>>()

    internal var currentPage = 1
    private val localRepository = LocalRepository(context)
    private val storeRepository = StoreRepository()
    private var currentCall: CustomCall<StoreCommentResponse>? = null

    internal fun isLogin() = localRepository.getAccessToken().isNotEmpty()

    internal fun getStoreComment(storeId: Long) {
        if (currentCall != null) {
            currentCall?.cancel()
            currentCall = null
        }
        currentCall = storeRepository.getStoreComments(localRepository.getUserInfo()?.id
                ?: -1, storeId, currentPage)
        updateProgressStatusObservable.onNext(true)
        currentCall?.enqueue(object : CustomCallback<StoreCommentResponse> {
            override fun success(call: Call<StoreCommentResponse>, response: Response<StoreCommentResponse>) {
                response.body()?.let {
                    storeComment.ratings.clear()
                    storeComment.ratings.addAll(it.ratings)
                    if (currentPage == 1) {
                        storeComment.comments.clear()
                    }
                    storeComment.comments.addAll(it.comments)
                    storeComment.nextPageFlag = it.nextPageFlag
                    getStoreCommentObservable.onNext(Notification.createOnNext(true))
                }
                updateProgressStatusObservable.onNext(false)
            }

            override fun onError(t: Throwable) {
                if (currentPage > 1) {
                    currentPage--
                }
                getStoreCommentObservable.onNext(Notification.createOnError(t))
                updateProgressStatusObservable.onNext(false)
            }
        })
    }

    internal fun comment(storeId: Long, comment: String) {
        storeRepository.storeComment(localRepository.getUserInfo()?.id
                ?: -1, storeId, comment)
                .observeOnUiThread()
                .subscribe({
                    storeComment.ratings.clear()
                    storeComment.ratings.addAll(it.ratings)
                    storeComment.comments.clear()
                    currentPage = 1
                    storeComment.comments.addAll(it.comments)
                    storeComment.nextPageFlag = it.nextPageFlag
                    getStoreCommentObservable.onNext(Notification.createOnNext(true))
                }, {
                    getStoreCommentObservable.onNext(Notification.createOnError(it))
                })
    }

    internal fun loadMore(storeId: Long, position: Int) {
        if (position > currentPage * 20 - 5) {
            currentPage++
            getStoreComment(storeId)
        }
    }
}
