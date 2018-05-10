package cao.cuong.supership.supership.ui.bill.activity

import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

class BillActivityUI : AnkoComponent<BillActivity> {
    override fun createView(ui: AnkoContext<BillActivity>) = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)
            id = R.id.billActivityContainer
            backgroundColorResource = R.color.colorWhite
        }
    }
}
