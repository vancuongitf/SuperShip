package cao.cuong.supership.supership.ui.location.shiproad

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.ShipAddress
import cao.cuong.supership.supership.data.model.google.AutoComplete
import cao.cuong.supership.supership.data.model.google.Direction
import cao.cuong.supership.supership.data.model.google.StoreAddress
import cao.cuong.supership.supership.data.model.rxevent.UpdateConfirmAddressEvent
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.getDistanceShipString
import cao.cuong.supership.supership.extension.getLastKnowLocation
import cao.cuong.supership.supership.extension.hideKeyBoard
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.location.LocationActivity
import cao.cuong.supership.supership.ui.location.LocationActivity.Companion.KEY_STORE_ADDRESS
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import io.reactivex.Notification
import org.jetbrains.anko.AnkoContext


class ShipRoadFragment : BaseFragment() {

    companion object {

        internal fun getNewInstance(storeAddress: StoreAddress): ShipRoadFragment {
            val instance = ShipRoadFragment()
            instance.arguments = Bundle().apply {
                this.putParcelable(KEY_STORE_ADDRESS, storeAddress)
            }
            return instance
        }
    }

    private lateinit var ui: ShipRoadFragmentUI
    private lateinit var storeAddress: StoreAddress
    private val viewModel = ShipRoadFragmentViewModel()
    private var googleMap: GoogleMap? = null
    private var shipAddress = ShipAddress(LatLng(0.0, 0.0), "", 0, "")

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        storeAddress = arguments.getParcelable(KEY_STORE_ADDRESS)
        ui = ShipRoadFragmentUI(viewModel.locations)
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
                        shipAddress.latLng = it
                        shipAddress.address = ""
                        shipAddress.distance = 0
                        updateConfirmViewUIStatus()
                        viewModel.getDirection(storeAddress.latLng, it)
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
                        .subscribe(this::handleUpdateProgressDialogStatus),
                viewModel.getDirectionObservable
                        .observeOnUiThread()
                        .subscribe(this::handleGetDirectionComplete)
        )
    }

    internal fun handleSearchViewTextChange(query: String) {
        viewModel.searchObservable.onNext(query.trim())
    }

    internal fun currentLocationClicked() {
        (activity as? BaseActivity)?.hideKeyBoard()
        context.getLastKnowLocation()
                .doOnSubscribe {
                    viewModel.updateProgressDialogStatus.onNext(true)
                }
                .doFinally {
                    viewModel.updateProgressDialogStatus.onNext(false)
                }
                .subscribe({
                    it?.let {
                        shipAddress.latLng = it
                        viewModel.getDirection(storeAddress.latLng, it)
                    }
                }, {})
    }

    internal fun chooseOnMapClicked() {
        if (ui.recyclerView.visibility == View.VISIBLE) {
            ui.recyclerView.visibility = View.GONE
        }
        (activity as? BaseActivity)?.hideKeyBoard()
        shipAddress.address = ""
        shipAddress.shipRoad = ""
        updateConfirmViewUIStatus()
    }

    internal fun eventConfirmAddress() {
        if (shipAddress.isValid()) {
            val intent = Intent()
            intent.putExtras(Bundle().apply {
                putParcelable(LocationActivity.KEY_ADDRESS_RESULT, shipAddress)
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
        shipAddress.shipRoad = ""
        shipAddress.distance = -1
        shipAddress.address = ""
        viewModel.getPlaceDetail(autoComplete)
                .subscribe({
                    ui.recyclerView.visibility = View.GONE
                    autoComplete.latLng = LatLng(it.placeDetail.geometry.location.lat, it.placeDetail.geometry.location.lng)
                    autoComplete.latLng?.let {
                        shipAddress.latLng = it
                        viewModel.getDirection(storeAddress.latLng, shipAddress.latLng)
                    }
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
        if (shipAddress.isValid()) {
            ui.tvAddress.text = shipAddress.address
            ui.tvDistance.text = shipAddress.distance.getDistanceShipString()
            ui.llConfirmAddress.visibility = View.VISIBLE
        } else {
            googleMap?.clear()
            ui.llConfirmAddress.visibility = View.GONE
        }
    }

    private fun handleGetDirectionComplete(notification: Notification<Direction>) {
        if (notification.isOnNext) {
            val value = notification.value
            if (value != null) {
                handleGetDirectionSuccess(value)
            } else {
                handleApiError(Throwable("Xãy ra lỗi. Vui lòng thử lại."))
            }
        } else if (notification.isOnError) {
            handleApiError(notification.error)
        }
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
                    shipAddress.distance = this.legs.first().distance.value
                    shipAddress.shipRoad = this.polyLine.points
                    shipAddress.address = this.legs.first().endAddress
                    this.polyLine.decodePoly().forEach {
                        options.add(it)
                    }
                    options.add(shipAddress.latLng)
                    googleMap?.addPolyline(options)
                    val marker2 = com.google.android.gms.maps.model.MarkerOptions()
                            .position(shipAddress.latLng)
                            .title("")
                            .icon(com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker(com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_CYAN))
                            .visible(true)
                    googleMap?.addMarker(marker2)
                    val builder = LatLngBounds.Builder()
                    builder.include(this.bounds.northeast.toLatLng())
                    builder.include(this.bounds.southwest.toLatLng())
                    val bounds = builder.build()
                    googleMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50))
                    updateConfirmViewUIStatus()
                }
            }
        }
    }
}