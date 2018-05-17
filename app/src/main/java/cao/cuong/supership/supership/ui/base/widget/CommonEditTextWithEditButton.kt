package cao.cuong.supership.supership.ui.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.annotation.DrawableRes
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

@SuppressLint("ViewConstructor")
/**
 *
 * @author at-cuongcao.
 */
class CommonEditTextWithEditButton(context: Context, @DrawableRes private val icon: Int, private val onClicked: () -> Unit = {}) : LinearLayout(context) {

    internal lateinit var editText: EditText

    init {
        AnkoContext.createDelegate(this).apply {
            verticalLayout {
                lparams(matchParent, wrapContent)

                linearLayout {
                    backgroundColorResource = R.color.colorCommonEditTextWithEditButtonBackGround
                    gravity = Gravity.CENTER_VERTICAL

                    imageView {
                        backgroundResource = icon
                    }.lparams(dimen(R.dimen.commonEditTextHieght), dimen(R.dimen.commonEditTextHieght)) {
                        horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    editText = editText {
                        textColorResource = R.color.colorBlack
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER_VERTICAL
                        singleLine = true
                        isEnabled = false
                    }.lparams(0, wrapContent) {
                        weight = 1f
                    }

                }.lparams(matchParent, wrapContent)

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
