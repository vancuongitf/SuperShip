package cao.cuong.supership.supership.ui.store.info

import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

class DrinkItemUI:AnkoComponent<ViewGroup> {

    internal lateinit var imgDrinkImage: ImageView
    internal lateinit var tvDrinkName: TextView
    internal lateinit var tvDrinkPrice: TextView
    internal lateinit var tvDrinkBillCount: TextView
    internal lateinit var imgOrderCount: ImageView

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {
            lparams(matchParent, dimen(R.dimen.drinkItemUIhight))
            gravity = Gravity.CENTER_VERTICAL
            padding = dimen(R.dimen.drinkItemUIPadding)

            imgDrinkImage = imageView {
                id = R.id.drinkItemImage
            }.lparams(dimen(R.dimen.drinkItemUIImageSize), dimen(R.dimen.drinkItemUIImageSize))

            verticalLayout {

                tvDrinkName = textView {
                    textColorResource = R.color.colorBlack
                    setTypeface(null, Typeface.BOLD)
                    textSizeDimen = R.dimen.drinkItemMainTextSize
                }

                view {

                }.lparams(matchParent, 0) {
                    weight = 1f
                }

                tvDrinkPrice = textView {
                    textColorResource = R.color.colorGray
                    setTypeface(null, Typeface.BOLD)
                    textSizeDimen = R.dimen.drinkItemMainTextSize
                }

                view {

                }.lparams(matchParent, 0) {
                    weight = 1f
                }

                linearLayout {
                    gravity = Gravity.CENTER_VERTICAL

                    imgOrderCount = imageView(R.drawable.ic_cart) {

                    }.lparams(dimen(R.dimen.drinkItemCartSize), dimen(R.dimen.drinkItemCartSize)) {
                        rightMargin = dimen(R.dimen.drinkItemUIPadding)
                    }

                    tvDrinkBillCount = textView {
                        textColorResource = R.color.colorGray
                        textSizeDimen = R.dimen.drinkItemSecondaryTextSize
                    }
                }

            }.lparams(matchParent, matchParent) {
                rightOf(R.id.drinkItemImage)
                leftMargin = dimen(R.dimen.drinkItemUIPadding)
            }
        }
    }
}
