package cao.cuong.supership.supership.ui.customer.home

import cao.cuong.supership.supership.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.relativeLayout

/**
 *
 * @author at-cuongcao.
 */
class HomeContainerUI : AnkoComponent<HomeContainer> {
    override fun createView(ui: AnkoContext<HomeContainer>) = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)
            id = R.id.homeContainer
        }
    }
}
