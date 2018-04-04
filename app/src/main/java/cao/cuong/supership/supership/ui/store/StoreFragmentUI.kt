package cao.cuong.supership.supership.ui.store

import cao.cuong.supership.supership.R
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar

/**
 *
 * @author at-cuongcao.
 */
class StoreFragmentUI : AnkoComponent<StoreFragment> {
    override fun createView(ui: AnkoContext<StoreFragment>) = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)

            toolbar {

            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))
        }
    }
}
