package cao.cuong.supership.supership.ui.customer.store.comment.rating

import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

class StarRatingItemUI : AnkoComponent<ViewGroup> {

    internal lateinit var tvStarValue: TextView
    internal lateinit var imgStar: ImageView
    internal lateinit var tvMessage: TextView
    internal lateinit var tvRatingCount: TextView

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {

        verticalLayout {
            lparams(matchParent, wrapContent)
            backgroundColorResource = R.color.colorGrayLight

            linearLayout {
                padding = dimen(R.dimen.accountFragmentLoginPadding)
                gravity = Gravity.CENTER_VERTICAL

                tvStarValue = textView {
                    textSizeDimen = R.dimen.ratingItemTextSize
                }

                imgStar = imageView {
                }.lparams(dimen(R.dimen.ratingItemStarSize), dimen(R.dimen.ratingItemStarSize)) {
                    horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }

                tvMessage = textView {
                    textSizeDimen = R.dimen.ratingItemTextSize
                }.lparams(0, wrapContent) {
                    weight = 1f
                }

                tvRatingCount = textView {
                    textSizeDimen = R.dimen.ratingItemTextSize
                    textColorResource = R.color.colorRed
                }
            }.lparams(matchParent, wrapContent)

            view {
                backgroundColorResource = R.color.colorWhite
            }.lparams(matchParent, dip(1))
        }
    }
}
