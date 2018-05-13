package cao.cuong.supership.supership.ui.location.shiproad

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.google.AutoComplete
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.extension.mapView
import cao.cuong.supership.supership.ui.location.search.LocationAdapter
import com.google.android.gms.maps.MapView
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.textChangedListener

class ShipRoadFragmentUI(locations: MutableList<AutoComplete>) : AnkoComponent<ShipRoadFragment> {

    internal lateinit var recyclerView: RecyclerView
    internal val locationAdapter = LocationAdapter(locations)
    internal lateinit var mapView: MapView
    internal lateinit var llConfirmAddress: LinearLayout
    internal lateinit var tvAddress: TextView
    internal lateinit var tvDistance: TextView

    override fun createView(ui: AnkoContext<ShipRoadFragment>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)
            padding = dimen(R.dimen.toolBarPadding)
            backgroundColorResource = R.color.colorGrayVeryLight

            toolbar {
                setContentInsetsAbsolute(0, 0)

                linearLayout {
                    backgroundResource = R.drawable.tool_bar_bg
                    gravity = Gravity.CENTER_VERTICAL
                    leftPadding = dimen(R.dimen.toolBarLeftPadding)

                    imageView(R.drawable.ic_search_black_36dp)
                            .lparams(dimen(R.dimen.toolBarSearchIconSize), dimen(R.dimen.toolBarSearchIconSize))

                    editText {
                        backgroundDrawable = null
                        backgroundColorResource = R.color.colorWhite
                        singleLine = true
                        hintResource = R.string.searchAddress

                        textChangedListener {
                            afterTextChanged {
                                owner.handleSearchViewTextChange(it.toString().trim())
                            }
                        }
                        onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                            Log.i("tag11", hasFocus.toString())
                        }
                    }.lparams(matchParent, wrapContent) {
                        leftMargin = dimen(R.dimen.toolBarLeftPadding)
                    }
                }.lparams(matchParent, matchParent)
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            relativeLayout {

                gravity = Gravity.CENTER_VERTICAL
                backgroundColorResource = R.color.colorWhite
                enableHighLightWhenClicked()
                onClick {
                    owner.currentLocationClicked()
                }

                imageView(R.drawable.ic_my_location) {
                    id = R.id.imgCurrentLocation
                }.lparams {
                    margin = dimen(R.dimen.accountFragmentLoginPadding)
                }

                textView(R.string.currentLocation) {
                    textColorResource = R.color.colorBlack
                }.lparams {
                    rightOf(R.id.imgCurrentLocation)
                    centerVertically()
                }

                view {
                    backgroundColorResource = R.color.colorGrayVeryLight
                }.lparams(matchParent, dip(1)) {
                    bottomOf(R.id.imgCurrentLocation)
                }
            }.lparams(matchParent, wrapContent) {
                topMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            relativeLayout {

                enableHighLightWhenClicked()
                gravity = Gravity.CENTER_VERTICAL
                backgroundColorResource = R.color.colorWhite
                enableHighLightWhenClicked()
                onClick {
                    owner.chooseOnMapClicked()
                }

                imageView(R.drawable.ic_choose_on_map) {
                    id = R.id.imgChooseOnMap
                }.lparams {
                    margin = dimen(R.dimen.accountFragmentLoginPadding)
                }

                textView(R.string.chooseOnMap) {
                    textColorResource = R.color.colorBlack
                }.lparams {
                    rightOf(R.id.imgChooseOnMap)
                    centerVertically()
                }

                view {
                    backgroundColorResource = R.color.colorGrayVeryLight
                }.lparams(matchParent, dip(1)) {
                    bottomOf(R.id.imgCurrentLocation)
                }
            }.lparams(matchParent, wrapContent)

            relativeLayout {

                mapView = mapView {
                }.lparams(matchParent, matchParent)

                recyclerView = recyclerView {
                    id = R.id.recyclerViewLocation
                    layoutManager = LinearLayoutManager(ctx)
                    adapter = locationAdapter
                    visibility = View.GONE
                }.lparams(matchParent, wrapContent)

                llConfirmAddress = verticalLayout {

                    padding = dimen(R.dimen.toolBarPadding)
                    visibility = View.GONE
                    backgroundColorResource = R.color.colorWhite

                    tvAddress = textView {
                        textColorResource = R.color.colorBlack
                        textSizeDimen = R.dimen.addressTextSize
                    }

                    tvDistance = textView {
                        textColorResource = R.color.colorBlack
                        textSizeDimen = R.dimen.addressTextSize
                    }

                    textView(R.string.confirmLocation) {
                        gravity = Gravity.CENTER_HORIZONTAL
                        backgroundColorResource = R.color.colorBlue
                        enableHighLightWhenClicked()
                        verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                        onClick {
                            owner.eventConfirmAddress()
                        }
                    }.lparams(matchParent, wrapContent) {
                        verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }
                }.lparams(matchParent, wrapContent) {
                    alignParentBottom()
                }
            }.lparams(matchParent, matchParent)

        }
    }
}
