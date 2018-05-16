package cao.cuong.supership.supership

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.ShipperRepository
import cao.cuong.supership.supership.data.source.remote.network.CustomCall
import cao.cuong.supership.supership.data.source.remote.network.CustomCallback
import cao.cuong.supership.supership.data.source.remote.request.CurrentLocationBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.getLastKnowLocation
import cao.cuong.supership.supership.ui.splash.splash.SplashFragment
import com.google.android.gms.maps.model.LatLng
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.TimeUnit

class AppViewModel(private val context: Context) {

    private val localRepository = LocalRepository(context)
    private val shipperRepository = ShipperRepository()
    private var currentCall: CustomCall<MessageResponse>? = null

    internal fun submitCurrentLocation() {
        context.getLastKnowLocation()
                .subscribeOn(Schedulers.io())
                .timeout(15, TimeUnit.SECONDS)
                .subscribe(this::submitCurrentLocation, {})
    }

    private fun submitCurrentLocation(latLng: LatLng?) {
        if (localRepository.getAccessToken().isEmpty()) {
            return
        }
        if (localRepository.getModule() == SplashFragment.SHIPPER_MODULE)
            latLng?.let {
                if (currentCall != null) {
                    currentCall?.cancel()
                    currentCall = null
                }
                currentCall = shipperRepository.submitCurrentLocation(CurrentLocationBody(localRepository.getAccessToken(), it.latitude, it.longitude))
                currentCall?.enqueue(object : CustomCallback<MessageResponse> {
                    override fun success(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    }

                    override fun onError(t: Throwable) {
                    }
                })
            }
    }
}
