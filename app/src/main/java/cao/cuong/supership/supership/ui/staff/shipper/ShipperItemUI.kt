package cao.cuong.supership.supership.ui.staff.shipper

import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

class ShipperItemUI : AnkoComponent<ViewGroup> {

    internal lateinit var imgAvatar: ImageView
    internal lateinit var tvId: TextView
    internal lateinit var tvName: TextView
    internal lateinit var tvAddress: TextView
    internal lateinit var tvPhone: TextView


    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {
            lparams(matchParent, dimen(R.dimen.storeItemUIHeight))
            linearLayout {
                padding = dimen(R.dimen.storeItemUIPadding)

                imgAvatar = imageView {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    visibility = View.GONE
                }.lparams(dimen(R.dimen.storeItemUIIconSize), dimen(R.dimen.storeItemUIIconSize))

                verticalLayout {
                    leftPadding = dimen(R.dimen.storeItemUIPadding)

                    tvName = textView {
                        textSizeDimen = R.dimen.defaultTextSize
                        setTypeface(typeface, Typeface.BOLD)
                        textColorResource = R.color.colorBlue
                    }

                    view().lparams(matchParent, 0) {
                        weight = 1f
                    }

                    tvId = textView {
                        textSizeDimen = R.dimen.secondaryTextSize
                        textColorResource = R.color.colorGray
                        singleLine = true
                    }.lparams(matchParent, wrapContent)

                    view().lparams(matchParent, 0) {
                        weight = 1f
                    }

                    tvAddress = textView {
                        textSizeDimen = R.dimen.secondaryTextSize
                        textColorResource = R.color.colorGray
                    }

                    view().lparams(matchParent, 0) {
                        weight = 1f
                    }

                    tvPhone = textView {
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
