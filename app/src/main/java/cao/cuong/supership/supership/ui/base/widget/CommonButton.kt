package cao.cuong.supership.supership.ui.base.widget

import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.StringRes
import android.view.Gravity
import android.widget.LinearLayout
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

/**
 *
 * @author at-cuongcao.
 */
class CommonButton(context: Context, @StringRes private val title: Int, @ColorRes private val background: Int, @DimenRes private val verticalPadding: Int) : LinearLayout(context) {

    init {
        AnkoContext.createDelegate(this).apply {
            relativeLayout {
                lparams(matchParent, wrapContent)
                backgroundColorResource = R.color.colorPink
                gravity = Gravity.CENTER
                verticalPadding = dimen(this@CommonButton.verticalPadding)

                textView(title) {
                    gravity = Gravity.CENTER_HORIZONTAL
                }.lparams(wrapContent, wrapContent)
            }
        }
    }
}
