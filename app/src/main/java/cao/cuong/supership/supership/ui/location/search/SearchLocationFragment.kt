package cao.cuong.supership.supership.ui.location.search

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.google.AutoComplete
import cao.cuong.supership.supership.data.model.google.StoreAddress
import cao.cuong.supership.supership.data.model.rxevent.UpdateConfirmAddressEvent
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.data.source.remote.response.google.GeoCodingResponse
import cao.cuong.supership.supership.extension.getLastKnowLocation
import cao.cuong.supership.supership.extension.hideKeyBoard
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.location.LocationActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.Notification
import org.jetbrains.anko.AnkoContext


class SearchLocationFragment : BaseFragment() {

    companion object {

        private const val KEY_ACTION = "key_store_address"

        internal fun getNewInstance(storeAddress: StoreAddress? = null): SearchLocationFragment {
            val instance = SearchLocationFragment()
            instance.arguments = Bundle().apply {
                storeAddress?.let {
                    this.putParcelable(KEY_ACTION, storeAddress)
                }
            }
            return instance
        }
    }

    private lateinit var ui: SearchLocationFragmentUI
    private val storeAddress = StoreAddress("", LatLng(0.0, 0.0))
    private val viewModel = SearchLocationFragmentViewModel()
    private var googleMap: GoogleMap? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = SearchLocationFragmentUI(viewModel.locations)
        ui.locationAdapter.onItemClicked = this::adapterOnItemClicked
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui.mapView.onCreate(savedInstanceState)
        ui.mapView.onResume()
        ui.mapView.getMapAsync({
            googleMap = it
            googleMap?.setOnMapClickListener {
                (activity as? BaseActivity)?.hideKeyBoard()
                if (ui.recyclerView.visibility == View.VISIBLE) {
                    ui.recyclerView.visibility = View.GONE
                } else {
                    it?.let {
                        storeAddress.latLng = it
                        storeAddress.address = ""
                        viewModel.getAddressByLatLng(it)
                                .observeOnUiThread()
                                .subscribe(this::handleGetAddressByLatLngSuccess, this::handleApiError)
                    }
                }
            }
            googleMap?.setMaxZoomPreference(5f)
            googleMap?.setMaxZoomPreference(20f)
        })
        RxBus.listen(UpdateConfirmAddressEvent::class.java)
                .observeOnUiThread()
                .subscribe({
                    updateConfirmViewUIStatus()
                }, {})
    }

    override fun onBindViewModel() {
        addDisposables(
                viewModel
                        .updateListObservable
                        .observeOnUiThread()
                        .subscribe(this::handleUpdateList),
                viewModel
                        .updateProgressDialogStatus
                        .observeOnUiThread()
                        .subscribe(this::handleUpdateProgressDialogStatus)
        )
    }

    internal fun handleSearchViewTextChange(query: String) {
        viewModel.searchObservable.onNext(query.trim())
    }

    internal fun currentLocationClicked() {
        (activity as? BaseActivity)?.hideKeyBoard()
            context.getLastKnowLocation()
                    .doOnSubscribe {
                        storeAddress.address = ""
                        updateConfirmViewUIStatus()
                        viewModel.updateProgressDialogStatus.onNext(true)
                    }
                    .doFinally {
                        viewModel.updateProgressDialogStatus.onNext(false)
                    }
                    .flatMap {
                        storeAddress.latLng = it
                        viewModel.getAddressByLatLng(it)
                    }.subscribe(this::handleGetAddressByLatLngSuccess, this::handleApiError)
    }

    internal fun chooseOnMapClicked() {
        if (ui.recyclerView.visibility == View.VISIBLE) {
            ui.recyclerView.visibility = View.GONE
        }
        (activity as? BaseActivity)?.hideKeyBoard()
        updateConfirmViewUIStatus()
    }

    internal fun eventConfirmAddress() {
        if (storeAddress.isValid()) {
            val intent = Intent()
            intent.putExtras(Bundle().apply {
                putParcelable(LocationActivity.KEY_ADDRESS_RESULT, storeAddress)
            })
            (activity as? LocationActivity)?.apply {
                setResult(RESULT_OK, intent)
                this.finish()
            }
        } else {
            ui.llConfirmAddress.visibility = View.GONE
        }
    }

    private fun adapterOnItemClicked(autoComplete: AutoComplete) {
        (activity as? BaseActivity)?.hideKeyBoard()
        viewModel.getPlaceDetail(autoComplete)
                .subscribe({
                    ui.recyclerView.visibility = View.GONE
                    storeAddress.address = autoComplete.description
                    storeAddress.latLng = it.placeDetail.geometry.location.toLatLng()
                        val marker = MarkerOptions()
                                .position(storeAddress.latLng)
                                .title(autoComplete.description)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                                .visible(true)
                        googleMap?.clear()
                        googleMap?.addMarker(marker)
                    googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(storeAddress.latLng, 15f))
                    updateConfirmViewUIStatus()
                }, this::handleApiError)
    }

    private fun handleUpdateList(notification: Notification<Boolean>) {
        if (notification.isOnNext) {
            ui.locationAdapter.notifyDataSetChanged()
            if (viewModel.locations.isNotEmpty()) {
                ui.recyclerView.visibility = View.VISIBLE
            } else {
                ui.recyclerView.visibility = View.GONE
            }
        }
    }

    private fun updateConfirmViewUIStatus() {
        if (storeAddress.isValid()) {
            ui.tvAddress.text = storeAddress.address
            ui.llConfirmAddress.visibility = View.VISIBLE
        } else {
            googleMap?.clear()
            ui.llConfirmAddress.visibility = View.GONE
        }
    }

    private fun handleGetAddressByLatLngSuccess(geoCoding: GeoCodingResponse) {
        if (geoCoding.results.isEmpty()) {
            return
        }
        storeAddress.address = geoCoding.results.first().address
        val marker = MarkerOptions()
                .position(storeAddress.latLng)
                .title(storeAddress.address)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                .visible(true)
        googleMap?.clear()
        googleMap?.addMarker(marker)
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(storeAddress.latLng, 15f))
        updateConfirmViewUIStatus()
    }
}
