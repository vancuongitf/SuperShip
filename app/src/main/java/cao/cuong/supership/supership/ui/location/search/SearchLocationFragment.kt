package cao.cuong.supership.supership.ui.location.search

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.google.AutoComplete
import cao.cuong.supership.supership.data.model.google.StoreAddress
import cao.cuong.supership.supership.extension.getLastKnowLocation
import cao.cuong.supership.supership.extension.observeOnUiThread
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

    private lateinit var ui: SearchLocationFragmentUI
    private val viewModel = SearchLocationFragmentViewModel()
    private var googleMap: GoogleMap? = null
    private var latLng: LatLng? = null
    private var address = ""

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
                if (ui.recyclerView.visibility == View.VISIBLE) {
                    ui.recyclerView.visibility = View.GONE
                } else {
                    it?.let {
                        latLng = it
                        viewModel.getAddressByLatLng(it)
                                .observeOnUiThread()
                                .subscribe({
                                    val marker = MarkerOptions()
                                            .position(latLng!!)
                                            .title(it.results[0].address)
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                                            .visible(true)
                                    googleMap?.clear()
                                    googleMap?.addMarker(marker)
                                    googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                                    updateConfirmViewUIStatus(latLng, it.results[0].address)
                                }, {})
                    }
                }
            }
            googleMap?.setMaxZoomPreference(5f)
            googleMap?.setMaxZoomPreference(20f)
        })
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
        var latLng: LatLng? = null
        context.getLastKnowLocation()
                .doOnSubscribe {
                    viewModel.updateProgressDialogStatus.onNext(true)
                }
                .doFinally {
                    viewModel.updateProgressDialogStatus.onNext(false)
                }
                .flatMap {
                    latLng = it
                    viewModel.getAddressByLatLng(it)
                }.subscribe({
                    val marker = MarkerOptions()
                            .position(latLng!!)
                            .title(it.results[0].address)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                            .visible(true)
                    googleMap?.clear()
                    googleMap?.addMarker(marker)
                    googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                    updateConfirmViewUIStatus(latLng, it.results[0].address)
                }, {})
    }

    internal fun chooseOnMapClicked() {
        if (ui.recyclerView.visibility == View.VISIBLE) {
            ui.recyclerView.visibility = View.GONE
        }
        updateConfirmViewUIStatus(null, "")
    }

    private fun adapterOnItemClicked(autoComplete: AutoComplete) {
        viewModel.getPlaceDetail(autoComplete)
                .subscribe({
                    ui.recyclerView.visibility = View.GONE
                    autoComplete.latLng = LatLng(it.placeDetail.geometry.location.lat, it.placeDetail.geometry.location.lng)
                    val marker = MarkerOptions()
                            .position(autoComplete.latLng!!)
                            .title(autoComplete.description)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                            .visible(true)
                    googleMap?.clear()
                    googleMap?.addMarker(marker)
                    googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(autoComplete.latLng, 15f))
                    updateConfirmViewUIStatus(autoComplete.latLng, autoComplete.description)
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

    private fun updateConfirmViewUIStatus(newLatLng: LatLng?, newAddress: String) {
        latLng = newLatLng
        address = newAddress
        if (latLng != null && address.isNotEmpty()) {
            ui.tvAddress.text = address
            ui.llConfirmAddress.visibility = View.VISIBLE
        } else {
            ui.llConfirmAddress.visibility = View.GONE
        }
    }

    internal fun eventConfirmAddress() {
        if (latLng != null && address.isNotEmpty()) {
            val address = StoreAddress(address, latLng!!)
            val intent = Intent()
            intent.putExtras(Bundle().apply {
                putParcelable(LocationActivity.KEY_ADDRESS_RESULT, address)
            })
            (activity as? LocationActivity)?.apply {
                setResult(RESULT_OK, intent)
                this.finish()
            }
        } else {
            ui.llConfirmAddress.visibility = View.GONE
        }
    }
}