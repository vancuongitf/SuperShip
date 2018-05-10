package cao.cuong.supership.supership.ui.location.search

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.RxEvent.UpdateConfirmAddressEvent
import cao.cuong.supership.supership.data.model.ShipAddress
import cao.cuong.supership.supership.data.model.google.AutoComplete
import cao.cuong.supership.supership.data.model.google.Direction
import cao.cuong.supership.supership.data.model.google.StoreAddress
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.data.source.remote.response.google.GeoCodingResponse
import cao.cuong.supership.supership.extension.*
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.location.LocationActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
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
    private lateinit var storeAddress: StoreAddress
    private val viewModel = SearchLocationFragmentViewModel()
    private var googleMap: GoogleMap? = null
    private var latLng: LatLng? = null
    private var address = ""
    private var action: Action = Action.SEARCH
    private var distance = 0L
    private var shipRoad = ""

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (arguments.containsKey(KEY_ACTION)) {
            storeAddress = arguments.getParcelable(KEY_ACTION)
            action = Action.SHIP_ROAD
        }
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
                        if (action == Action.SEARCH) {
                            viewModel.getAddressByLatLng(it)
                                    .observeOnUiThread()
                                    .subscribe(this::handleGetAddressByLatLngSuccess, this::handleApiError)
                        } else {
                            viewModel.getDirection(storeAddress.latLng, it)
                                    .observeOnUiThread()
                                    .subscribe(this::handleGetDirectionSuccess, this::handleApiError)
                        }
                    }
                }
            }
            googleMap?.setMaxZoomPreference(5f)
            googleMap?.setMaxZoomPreference(20f)
        })
        RxBus.listen(UpdateConfirmAddressEvent::class.java)
                .observeOnUiThread()
                .subscribe({
                    updateConfirmViewUIStatus(latLng, "")
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
        if (action == Action.SEARCH) {
            context.getLastKnowLocation()
                    .doOnSubscribe {
                        viewModel.updateProgressDialogStatus.onNext(true)
                    }
                    .doFinally {
                        viewModel.updateProgressDialogStatus.onNext(false)
                    }
                    .flatMap {
                        viewModel.getAddressByLatLng(it)
                    }.subscribe(this::handleGetAddressByLatLngSuccess, this::handleApiError)
        } else {
            context.getLastKnowLocation()
                    .doOnSubscribe {
                        viewModel.updateProgressDialogStatus.onNext(true)
                    }
                    .doFinally {
                        viewModel.updateProgressDialogStatus.onNext(false)
                    }
                    .flatMap {
                        viewModel.getDirection(storeAddress.latLng, it)
                    }.subscribe(this::handleGetDirectionSuccess, this::handleApiError)
        }
    }

    internal fun chooseOnMapClicked() {
        if (ui.recyclerView.visibility == View.VISIBLE) {
            ui.recyclerView.visibility = View.GONE
        }
        (activity as? BaseActivity)?.hideKeyBoard()
        updateConfirmViewUIStatus(null, "")
    }

    internal fun eventConfirmAddress() {
        if (latLng != null && address.isNotEmpty()) {
            val address = StoreAddress(address, latLng!!)
            val intent = Intent()
            intent.putExtras(Bundle().apply {
                if (action == Action.SEARCH) {
                    putParcelable(LocationActivity.KEY_ADDRESS_RESULT, address)
                } else {
                    putParcelable(LocationActivity.KEY_ADDRESS_RESULT, ShipAddress(latLng!!, this@SearchLocationFragment.address, distance, shipRoad))
                }
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
                    autoComplete.latLng = LatLng(it.placeDetail.geometry.location.lat, it.placeDetail.geometry.location.lng)
                    if (action == Action.SEARCH) {
                        val marker = MarkerOptions()
                                .position(autoComplete.latLng!!)
                                .title(autoComplete.description)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                                .visible(true)
                        googleMap?.clear()
                        googleMap?.addMarker(marker)
                        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(autoComplete.latLng, 15f))
                    } else {
                        autoComplete.latLng?.let {
                            viewModel.getDirection(storeAddress.latLng, it)
                                    .observeOnUiThread()
                                    .subscribe(this::handleGetDirectionSuccess, this::handleApiError)
                        }
                    }
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

    private fun updateConfirmViewUIStatus(newLatLng: LatLng?, newAddress: String, distance: Long = 0) {
        latLng = newLatLng
        address = newAddress
        if (latLng != null && address.isNotEmpty()) {
            ui.tvAddress.text = address
            ui.llConfirmAddress.visibility = View.VISIBLE
            if (action == Action.SHIP_ROAD) {
                this.distance = distance
                ui.tvDistance.visibility = View.VISIBLE
                ui.tvDistance.text = "Khoảng cách: ${distance.getDistanceShipString()}     Phí ship: ${distance.getShipFee()} VND"
            }
        } else {
            googleMap?.clear()
            ui.llConfirmAddress.visibility = View.GONE
        }
    }

    private fun handleGetAddressByLatLngSuccess(geoCoding: GeoCodingResponse) {
        if (geoCoding.results.isEmpty()) {
            return
        }
        val marker = MarkerOptions()
                .position(latLng!!)
                .title(geoCoding.results[0].address)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                .visible(true)
        googleMap?.clear()
        googleMap?.addMarker(marker)
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        updateConfirmViewUIStatus(latLng, geoCoding.results[0].address)
    }

    private fun handleGetDirectionSuccess(direction: Direction) {
        if (direction.status != "OK" || direction.routes.isEmpty()) {
            handleApiError(Throwable("Không định vị được tuyến đường. Vui lòng thử lại hoặc chọn địa chỉ mới."))
            return
        }
        if (direction.routes.isNotEmpty()) {
            with(direction.routes.first()) {
                val marker = com.google.android.gms.maps.model.MarkerOptions()
                        .position(storeAddress.latLng)
                        .title(storeAddress.address)
                        .icon(com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker(com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_CYAN))
                        .visible(true)
                googleMap?.clear()
                googleMap?.addMarker(marker)
                if (this.polyLine.points.isNotEmpty()) {
                    val options = com.google.android.gms.maps.model.PolylineOptions().width(5f).color(android.graphics.Color.BLUE).geodesic(true)
                    options.add(storeAddress.latLng)
                    shipRoad = this.polyLine.points
                    this.polyLine.decodePoly().forEach {
                        options.add(it)
                    }
                    options.add(latLng)
                    googleMap?.addPolyline(options)
                    val marker2 = com.google.android.gms.maps.model.MarkerOptions()
                            .position(latLng!!)
                            .title("")
                            .icon(com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker(com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_CYAN))
                            .visible(true)
                    googleMap?.addMarker(marker2)
                    val builder = LatLngBounds.Builder()
                    builder.include(this.bounds.northeast.toLatLng())
                    builder.include(this.bounds.southwest.toLatLng())
                    val bounds = builder.build()
                    googleMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50))
                    updateConfirmViewUIStatus(latLng, this.legs.first().endAddress, this.legs.first().distance.value)
                }
            }
        }
    }

    enum class Action() {
        SEARCH,
        SHIP_ROAD
    }
}