package cao.cuong.supership.supership.ui.shipper.account

import cao.cuong.supership.supership.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.relativeLayout

class ShipperActivityUI : AnkoComponent<ShipperActivity> {
    override fun createView(ui: AnkoContext<ShipperActivity>) = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)
            id = R.id.shipperActivityContainer
        }
    }
}
