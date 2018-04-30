package cao.cuong.supership.supership.ui.location

import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

class LocationActivityUI : AnkoComponent<LocationActivity> {
    override fun createView(ui: AnkoContext<LocationActivity>) = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)
            backgroundColorResource = R.color.colorWhite
            id = R.id.searchLocationContainer
        }
    }
}
