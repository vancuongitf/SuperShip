package cao.cuong.supership.supership.ui.staff

import cao.cuong.supership.supership.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.relativeLayout

class StaffActivityUI : AnkoComponent<StaffActivity> {
    override fun createView(ui: AnkoContext<StaffActivity>) = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)
            id = R.id.staffActivityContainer
        }
    }
}
