package cao.cuong.supership.supership.ui.store.info

import android.view.ViewGroup
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

class HeaderItemUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {
            lparams(matchParent, dip(100))

            backgroundColorResource = R.color.colorAccent
        }
    }
}