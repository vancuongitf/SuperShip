package cao.cuong.supership.supership.ui.store.info

import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

class MenuHeaderItemUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {
            lparams(matchParent, dimen(R.dimen.toolBarHeight))
            backgroundColorResource  = R.color.colorRed

            textView {
                text = "hello"
            }
        }
    }
}
