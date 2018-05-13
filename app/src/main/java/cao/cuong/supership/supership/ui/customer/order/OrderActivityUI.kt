package cao.cuong.supership.supership.ui.customer.order

import cao.cuong.supership.supership.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.relativeLayout

class OrderActivityUI:AnkoComponent<OrderActivity> {
    override fun createView(ui: AnkoContext<OrderActivity>) = with(ui){
        relativeLayout {
            id = R.id.orderActivityContainer
            lparams(matchParent, matchParent)
        }
    }
}
