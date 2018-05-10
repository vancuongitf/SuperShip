package cao.cuong.supership.supership.ui.bill

import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

/**
 *
 * @author at-cuongcao.
 */
class BillItemUI : AnkoComponent<ViewGroup> {

    internal lateinit var imgStoreIcon: ImageView
    internal lateinit var tvStoreName: TextView
    internal lateinit var tvBillId: TextView
    internal lateinit var tvBillStatus: TextView
    internal lateinit var tvBillPrice: TextView

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
                        textColorResource = R.color.colorBlue
                    }

                    view().lparams(matchParent, 0) {
                        weight = 1f
                    }

                    tvBillId = textView {
                        textSizeDimen = R.dimen.secondaryTextSize
                        textColorResource = R.color.colorGray
                        singleLine = true
                    }.lparams(matchParent, wrapContent)

                    view().lparams(matchParent, 0) {
                        weight = 1f
                    }

                    tvBillPrice = textView {
                        textSizeDimen = R.dimen.secondaryTextSize
                        textColorResource = R.color.colorGray
                    }

                    view().lparams(matchParent, 0) {
                        weight = 1f
                    }

                    tvBillStatus = textView {
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