package cao.cuong.supership.supership.ui.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.annotation.StringRes
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

@SuppressLint("ViewConstructor")
/**
 *
 * @author at-cuongcao.
 */
class CommonEditText(context: Context, @StringRes private val title: Int, private val passWordType: Boolean) : LinearLayout(context) {

    internal lateinit var editText: EditText

    init {
        AnkoContext.createDelegate(this).apply {
            verticalLayout {
                lparams(matchParent, wrapContent)

                textView(title) {
                    textColorResource = R.color.colorGray
                    bottomPadding = dip(2)
                }

                editText = editText {
                    textColorResource = R.color.colorBlack
                    backgroundColorResource = R.color.commonEditBackground
                    verticalPadding = dip(5)
                    singleLine = true
                }

                if (passWordType) {
                    editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
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
