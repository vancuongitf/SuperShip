package cao.cuong.supership.supership.ui.location.search

import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

class LocationItemUI : AnkoComponent<ViewGroup> {

    internal lateinit var tvMainText: TextView
    internal lateinit var tvSecondary: TextView

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {
            lparams(matchParent, wrapContent)
            gravity = Gravity.CENTER_VERTICAL
            backgroundColorResource = R.color.colorWhite

            imageView(R.drawable.ic_marker_gray) {
                id = R.id.imgChooseOnMap
            }.lparams {
                margin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            tvMainText = textView {
                id = R.id.tvMainText
                textColorResource = R.color.colorBlack
                singleLine = true
            }.lparams {
                rightOf(R.id.imgChooseOnMap)
            }

            tvSecondary = textView {
                id = R.id.tvSecondaryText
                textColorResource = R.color.colorGray
                singleLine = true
            }.lparams {
                rightOf(R.id.imgChooseOnMap)
                bottomOf(R.id.tvMainText)
                topMargin = dimen(R.dimen.toolBarPadding)
            }

            view {
                backgroundColorResource = R.color.colorGrayVeryLight
            }.lparams(matchParent, dip(1)) {
                bottomOf(R.id.imgCurrentLocation)
            }
        }
    }
}