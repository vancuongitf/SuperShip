package cao.cuong.supership.supership.ui.customer.store.activity

import cao.cuong.supership.supership.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.relativeLayout

class StoreActivityUI : AnkoComponent<StoreActivity> {
    override fun createView(ui: AnkoContext<StoreActivity>) = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)
            id = R.id.storeActivityContainer
        }
    }
}
