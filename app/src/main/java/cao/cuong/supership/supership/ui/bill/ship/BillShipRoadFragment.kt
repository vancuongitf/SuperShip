package cao.cuong.supership.supership.ui.bill.ship

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.Address
import cao.cuong.supership.supership.data.model.google.Polyline
import cao.cuong.supership.supership.ui.base.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import org.jetbrains.anko.AnkoContext

class BillShipRoadFragment : BaseFragment() {

    companion object {

        private const val KEY_STORE_ADDRESS = "store_address"
        private const val KEY_BILL_ADDRESS = "bill_address"
        private const val KEY_SHIP_ROAD = "ship_road"

        internal fun getNewInstance(storeAddress: Address, billAddress: Address, shipRoad: String): BillShipRoadFragment {
            val instance = BillShipRoadFragment()
            instance.arguments = Bundle().apply {
                putParcelable(KEY_STORE_ADDRESS, storeAddress)
                putParcelable(KEY_BILL_ADDRESS, billAddress)
                putString(KEY_SHIP_ROAD, shipRoad)
            }
            return instance
        }
    }

    private lateinit var ui: BillShipRoadFragmentUI
    private lateinit var storeAddress: Address
    private lateinit var billAddress: Address
    private lateinit var shipRoadPolyLine: Polyline
    private var googleMap: GoogleMap? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        storeAddress = arguments.getParcelable(KEY_STORE_ADDRESS)
        billAddress = arguments.getParcelable(KEY_BILL_ADDRESS)
        shipRoadPolyLine = Polyline(arguments.getString(KEY_SHIP_ROAD))
        ui = BillShipRoadFragmentUI()
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui.mapView.onCreate(savedInstanceState)
        ui.mapView.onResume()
        ui.mapView.getMapAsync({
            googleMap = it
            googleMap?.clear()
            val marker = MarkerOptions()
                    .position(LatLng(storeAddress.lat, storeAddress.lng))
                    .title(storeAddress.address)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                    .visible(true)
            googleMap?.addMarker(marker)
            val marker2 = MarkerOptions()
                    .position(LatLng(billAddress.lat, billAddress.lng))
                    .title(billAddress.address)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                    .visible(true)
            googleMap?.addMarker(marker2)


            if (shipRoadPolyLine.points.isNotEmpty()) {
                val options = PolylineOptions().width(5f).color(android.graphics.Color.BLUE).geodesic(true)
                options.add(LatLng(storeAddress.lat, storeAddress.lng))
                this.shipRoadPolyLine.decodePoly().forEach {
                    options.add(it)
                }
                options.add(LatLng(billAddress.lat, billAddress.lng))
                googleMap?.addPolyline(options)
                val markers = com.google.android.gms.maps.model.MarkerOptions()
                        .position(LatLng(billAddress.lat, billAddress.lng))
                        .title(billAddress.address)
                        .icon(com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker(com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_CYAN))
                        .visible(true)
                googleMap?.addMarker(markers)
                val builder = LatLngBounds.Builder()
                builder.include(LatLng(billAddress.lat, billAddress.lng))
                builder.include(LatLng(storeAddress.lat, storeAddress.lng))
                val bounds = builder.build()
                googleMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50))
            }
        })
    }

    override fun onBindViewModel() {

    }

    internal fun onBackClicked() {
        activity.onBackPressed()
    }
}