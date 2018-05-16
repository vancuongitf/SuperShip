package cao.cuong.supership.supership.ui.splash

import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import cao.cuong.supership.supership.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.relativeLayout

/**
 *
 * @author at-cuongcao.
 */
class SplashActivityUI : AnkoComponent<SplashActivity> {

    override fun createView(ui: AnkoContext<SplashActivity>) = with(ui) {
        relativeLayout {
            id = R.id.splashActivityContainer
            lparams(matchParent, matchParent)
        }
    }
}