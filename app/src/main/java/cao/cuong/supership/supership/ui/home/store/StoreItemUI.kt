package cao.cuong.supership.supership.ui.home.store

import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

/**
 *
 * @author at-cuongcao.
 */
class StoreItemUI : AnkoComponent<ViewGroup> {

    internal lateinit var imgStoreIcon: ImageView
    internal lateinit var tvStoreName: TextView
    internal lateinit var tvStoreAddress: TextView
    internal lateinit var tvStoreDistance: TextView
    internal lateinit var imgStoreStarIcon: ImageView
    internal lateinit var tvStoreStarRate: TextView

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {
            lparams(matchParent, dimen(R.dimen.storeItemUIHeight))
            linearLayout {
                padding = dimen(R.dimen.storeItemUIPadding)

                imgStoreIcon = imageView {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }.lparams(dimen(R.dimen.storeItemUIIconSize), dimen(R.dimen.storeItemUIIconSize))

                verticalLayout {
                    leftPadding = dimen(R.dimen.storeItemUIPadding)

                    tvStoreName = textView {
                        textSizeDimen = R.dimen.defaultTextSize
                        setTypeface(typeface, Typeface.BOLD)
                        textColorResource = R.color.colorBlack
                    }

                    linearLayout {

                        tvStoreAddress = textView {
                            textSizeDimen = R.dimen.secondaryTextSize
                            textColorResource = R.color.colorGray
                            maxLines = 2
                        }.lparams(0, wrapContent) {
                            weight = 1f
                            rightMargin = dimen(R.dimen.storeItemUIPadding)
                        }

                        tvStoreDistance = textView {
                            textSizeDimen = R.dimen.secondaryTextSize
                            textColorResource = R.color.colorGray
                        }
                    }.lparams(matchParent, wrapContent)

                    view().lparams(matchParent, 0) {
                        weight = 1f
                    }

                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL
                        imgStoreStarIcon = imageView(R.drawable.ic_star_gold)
                                .lparams(dimen(R.dimen.storeItemUIStarIconSize), dimen(R.dimen.storeItemUIStarIconSize)) {
                                    rightMargin = dimen(R.dimen.storeItemUITvAddressTopMargin)
                                }

                        tvStoreStarRate = textView {
                            textSizeDimen = R.dimen.secondaryTextSize
                            textColorResource = R.color.colorBlue
                        }
                    }.lparams(matchParent, wrapContent)
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