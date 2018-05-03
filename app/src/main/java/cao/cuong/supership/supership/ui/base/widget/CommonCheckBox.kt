package cao.cuong.supership.supership.ui.base.widget

import android.content.Context
import android.graphics.Typeface.BOLD
import android.view.Gravity
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

class CommonCheckBox(context: Context) : LinearLayout(context) {

    internal lateinit var checkBox: CheckBox
    internal lateinit var tvPrice: TextView

    init {
        AnkoContext.createDelegate(this).apply {
            linearLayout {
                lparams(matchParent, wrapContent)
                gravity = Gravity.CENTER_VERTICAL

                checkBox = checkBox {
                    verticalPadding = dimen(R.dimen.drinkItemUIPadding)
                    singleLine = true
                }.lparams(0, wrapContent) {
                    weight = 1f
                    leftMargin = dip(-5)
                }

                tvPrice = textView {
                    setTypeface(null, BOLD)
                    textColorResource = R.color.colorBlack
                }
            }
        }
    }
}
