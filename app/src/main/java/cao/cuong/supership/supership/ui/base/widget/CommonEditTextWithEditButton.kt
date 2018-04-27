package cao.cuong.supership.supership.ui.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.annotation.DrawableRes
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

@SuppressLint("ViewConstructor")
/**
 *
 * @author at-cuongcao.
 */
class CommonEditTextWithEditButton(context: Context, @DrawableRes private val icon: Int, private val onClicked: () -> Unit = {}) : LinearLayout(context) {

    internal lateinit var editText: EditText
    internal lateinit var imgEdit: ImageView

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

                    imgEdit = imageView {
                        backgroundResource = R.drawable.ic_edit

                        onClick {
                            if (editText.isEnabled) {
                                onClicked()
                            } else {
                                editText.isEnabled = true
                                backgroundResource = R.drawable.ic_check
                            }
                        }
                    }.lparams(dimen(R.dimen.commonEditTextHieght), dimen(R.dimen.commonEditTextHieght)) {
                        margin = dimen(R.dimen.accountFragmentLoginPadding)
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
