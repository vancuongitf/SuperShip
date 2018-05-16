package cao.cuong.supership.supership.ui.splash.splash

import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

class SplashFragmentUI : AnkoComponent<SplashFragment> {

    internal lateinit var tvAppName: TextView
    internal lateinit var imgShipper: ImageView

    override fun createView(ui: AnkoContext<SplashFragment>) = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)
            gravity = Gravity.CENTER

            tvAppName = textView(R.string.app_name) {
                id = R.id.splashActivityTvAppName
                textColorResource = R.color.colorRed
                textSizeDimen = R.dimen.splashActivityAppNameSize
            }

            imgShipper = imageView(R.drawable.ic_shipper) {
                id = R.id.splashActivityImgShipperIcon
            }.lparams(dip(60), dip(60)) {
                bottomOf(tvAppName)
            }

            textView(R.string.app_slogan) {
                id = R.id.splashActivityTvAppSlogan
                textColorResource = R.color.colorBlue
                gravity = Gravity.CENTER_HORIZONTAL
                textSizeDimen = R.dimen.splashActivityAppSloganSize
            }.lparams {
                bottomOf(imgShipper)
            }
        }
    }
}
