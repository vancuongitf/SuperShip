package cao.cuong.supership.supership.ui.location.shiproad

import cao.cuong.supership.supership.data.model.google.AutoComplete
import cao.cuong.supership.supership.data.model.google.Direction
import cao.cuong.supership.supership.data.model.rxevent.UpdateConfirmAddressEvent
import cao.cuong.supership.supership.data.source.GoogleMapRepository
import cao.cuong.supership.supership.data.source.remote.network.CustomCall
import cao.cuong.supership.supership.data.source.remote.network.CustomCallback
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.data.source.remote.response.google.AutoCompleteResponse
import cao.cuong.supership.supership.extension.observeOnUiThread
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Notification
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class ShipRoadFragmentViewModel {

    internal val updateListObservable = PublishSubject.create<Notification<Boolean>>()
    internal val searchObservable = PublishSubject.create<String>()
    internal val updateProgressDialogStatus = BehaviorSubject.create<Boolean>()
    internal val getDirectionObservable = PublishSubject.create<Notification<Direction>>()
    internal val locations = mutableListOf<AutoComplete>()
    private val googleMapRepository = GoogleMapRepository()
    private var currentSearchCall: CustomCall<AutoCompleteResponse>? = null
    private var currentDirectionCall: CustomCall<Direction>? = null

    init {
        initSearchObservable()
    }

    internal fun getPlaceDetail(autoComplete: AutoComplete) = googleMapRepository.getPlaceDetail(autoComplete.placeId)
            .observeOnUiThread()
            .doOnSubscribe {
                updateProgressDialogStatus.onNext(true)
                RxBus.publish(UpdateConfirmAddressEvent())
            }
            .doFinally {
                updateProgressDialogStatus.onNext(false)
            }

    internal fun getDirection(from: LatLng, to: LatLng) {
        callGetDirectionApi(from, to)
    }

    private fun initSearchObservable() {
        searchObservable
                .observeOn(Schedulers.computation())
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .flatMap {
                    callSearchApi(it)
                }.subscribe({
                    updateListObservable.onNext(it)
                }, {
                    updateListObservable.onNext(Notification.createOnError(it))
                })
    }

    private fun callSearchApi(query: String): PublishSubject<Notification<Boolean>> {
        if (currentSearchCall != null || query.isEmpty()) {
            currentSearchCall?.cancel()
        }
        val result = PublishSubject.create<Notification<Boolean>>()
        currentSearchCall = googleMapRepository.searchLocation(query)
        currentSearchCall?.enqueue(object : CustomCallback<AutoCompleteResponse> {
            override fun success(call: Call<AutoCompleteResponse>, response: Response<AutoCompleteResponse>) {
                val data = response.body()
                if (data != null) {
                    locations.clear()
                    locations.addAll(data.predictions)
                    result.onNext(Notification.createOnNext(true))
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

    private fun callGetDirectionApi(from: LatLng, to: LatLng) {
        thread {
            if (currentDirectionCall != null) {
                currentDirectionCall?.cancel()
            }
            currentDirectionCall = googleMapRepository.getDirection(from, to)
            currentDirectionCall?.enqueue(object : CustomCallback<Direction> {
                override fun success(call: Call<Direction>, response: Response<Direction>) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        getDirectionObservable.onNext(Notification.createOnNext(responseBody))
                    } else {
                        getDirectionObservable.onNext(Notification.createOnError(Throwable("Xãy ra lỗi.")))
                    }
                }

                override fun onError(t: Throwable) {
                    getDirectionObservable.onNext(Notification.createOnError(t))
                }
            })
        }
    }
}
