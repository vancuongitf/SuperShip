package cao.cuong.supership.supership.ui.store.info

import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

class DrinkItemUI:AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {
            lparams(matchParent, wrapContent)

            textView(R.string.notification){

            }.lparams{
                centerInParent()
            }
        }
    }
}