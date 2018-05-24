package cao.cuong.supership.supership.ui.customer.bill.ship

import android.view.Gravity
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.extension.mapView
import com.google.android.gms.maps.MapView
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.sdk25.coroutines.onClick

class BillShipRoadFragmentUI : AnkoComponent<BillShipRoadFragment> {

    internal lateinit var mapView: MapView

    override fun createView(ui: AnkoContext<BillShipRoadFragment>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)

            toolbar {
                setContentInsetsAbsolute(0, 0)
                backgroundColorResource = R.color.colorGrayLight

                linearLayout {

                    gravity = Gravity.CENTER_VERTICAL

                    imageView(R.drawable.ic_back_button) {
                        enableHighLightWhenClicked()
                        onClick {
                            owner.onBackClicked()
                        }
                    }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                        horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    textView(R.string.shipRoad) {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorBlue
                    }.lparams(0, wrapContent) {
                        weight = 1f
                    }
                }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            }.lparams(matchParent, wrapContent)

            view {
                backgroundResource = R.color.colorGrayVeryLight
            }.lparams(matchParent, dip(1))

            mapView = mapView {
            }.lparams(matchParent, matchParent)
        }
    }
}
