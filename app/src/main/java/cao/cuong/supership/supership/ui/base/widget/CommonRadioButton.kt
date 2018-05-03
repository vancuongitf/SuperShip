package cao.cuong.supership.supership.ui.base.widget

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange

class CommonRadioButton(context: Context) : LinearLayout(context) {
    internal lateinit var radio: RadioButton
    internal lateinit var tvPrice: TextView
    internal var onCheckedListener: () -> Unit = {}

    init {
        AnkoContext.createDelegate(this).apply {
            linearLayout {
                lparams(matchParent, wrapContent)
                gravity = Gravity.CENTER_VERTICAL

                radio = radioButton {
                    verticalPadding = dimen(R.dimen.drinkItemUIPadding)
                    singleLine = true
                    onCheckedChange { _, _ ->
                        onCheckedListener()
                    }
                }.lparams(0, wrapContent) {
                    weight = 1f
                    leftMargin = dip(-5)
                }

                tvPrice = textView {
                    setTypeface(null, Typeface.BOLD)
                    textColorResource = R.color.colorBlack
                }
            }
        }
    }
}
