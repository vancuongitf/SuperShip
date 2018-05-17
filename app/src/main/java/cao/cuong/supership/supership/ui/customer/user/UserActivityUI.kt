package cao.cuong.supership.supership.ui.customer.user

import cao.cuong.supership.supership.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.relativeLayout

class UserActivityUI : AnkoComponent<UserActivity> {
    override fun createView(ui: AnkoContext<UserActivity>) = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)
            id = R.id.userActivityContainer
        }
    }
}
