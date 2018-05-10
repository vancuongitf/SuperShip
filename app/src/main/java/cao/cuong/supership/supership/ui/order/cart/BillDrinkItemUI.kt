package cao.cuong.supership.supership.ui.order.cart

import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

class BillDrinkItemUI : AnkoComponent<ViewGroup> {
    internal lateinit var imgDrinkImage: ImageView
    internal lateinit var tvDrinkName: TextView
    internal lateinit var tvDrinkPrice: TextView
    internal lateinit var tvDrinkCount: TextView
    internal lateinit var tvTotalPrice: TextView
    internal lateinit var imgMinus: ImageView
    internal lateinit var imgAdd: ImageView

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {
            lparams(matchParent, dimen(R.dimen.storeItemUIHeight))
            backgroundColorResource = R.color.colorWhite
            linearLayout {
                padding = dimen(R.dimen.storeItemUIPadding)

                imgDrinkImage = imageView {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }.lparams(dimen(R.dimen.storeItemUIIconSize), dimen(R.dimen.storeItemUIIconSize))

                verticalLayout {
                    leftPadding = dimen(R.dimen.storeItemUIPadding)

                    tvDrinkName = textView {
                        textSizeDimen = R.dimen.defaultTextSize
                        setTypeface(typeface, Typeface.BOLD)
                        textColorResource = R.color.colorBlue
                    }

                    view().lparams(matchParent, 0) {
                        weight = 1f
                    }

                    tvDrinkPrice = textView {
                        textSizeDimen = R.dimen.secondaryTextSize
                        textColorResource = R.color.colorBlack
                        singleLine = true
                    }.lparams(matchParent, wrapContent)

                    view().lparams(matchParent, 0) {
                        weight = 1f
                    }

                    linearLayout {

                        gravity = Gravity.CENTER_VERTICAL

                        textView(R.string.count) {
                            textSizeDimen = R.dimen.secondaryTextSize
                            textColorResource = R.color.colorBlack
                        }

                        imgMinus = imageView(R.drawable.ic_minus_black) {
                        }.lparams(dimen(R.dimen.addDrinkButtonSize), dimen(R.dimen.addDrinkButtonSize)) {
                            horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }

                        tvDrinkCount = textView {
                            textColorResource = R.color.colorBlack
                            setTypeface(null, Typeface.BOLD)
                        }.lparams {
                            horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }

                        imgAdd = imageView(R.drawable.ic_add_black) {
                        }.lparams(dimen(R.dimen.addDrinkButtonSize), dimen(R.dimen.addDrinkButtonSize)) {
                            horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }

                    }.lparams(matchParent, wrapContent)

                    view().lparams(matchParent, 0) {
                        weight = 1f
                    }

                    tvTotalPrice = textView {
                        textSizeDimen = R.dimen.secondaryTextSize
                        textColorResource = R.color.colorBlack
                    }
                }.lparams(matchParent, matchParent)
            }.lparams(matchParent, dimen(R.dimen.storeItemUIContentHeight))

            view {
                backgroundColorResource = R.color.colorGrayVeryLight
            }.lparams(matchParent, dip(1)) {
                alignParentBottom()
            }
        }
    }
}
