package cao.cuong.supership.supership.ui.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.StringRes
import android.widget.LinearLayout
import android.widget.TextView
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

@SuppressLint("ViewConstructor")
/**
 *
 * @author at-cuongcao.
 */
class CommonTextView(context: Context, @StringRes private val title: Int, private val passWordType: Boolean) : LinearLayout(context) {

    internal lateinit var textView: TextView

    init {
        AnkoContext.createDelegate(this).apply {
            verticalLayout {
                lparams(matchParent, wrapContent)

                textView(title) {
                    textColorResource = R.color.colorGray
                    bottomPadding = dip(2)
                }

                textView = textView {
                    backgroundColorResource = R.color.commonEditBackground
                    textColorResource = R.color.colorBlack
                    verticalPadding = dip(5)
                    singleLine = true
                }

                view {
                    backgroundResource = R.color.colorRed
                    alpha = 0.7f
                }.lparams(matchParent, dip(1)) {
                    bottomMargin = dip(2)
                }
            }
        }
    }
}
