package cao.cuong.supership.supership.ui.test

import android.view.View
import android.widget.ImageView
import cao.cuong.supership.supership.extension.getWidthScreen
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class TestActivityUI : AnkoComponent<TestActivity> {

    lateinit var img: ImageView

    override fun createView(ui: AnkoContext<TestActivity>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)

            img = imageView {
                scaleType = ImageView.ScaleType.CENTER_CROP
            }.lparams(ctx.getWidthScreen(), ctx.getWidthScreen()) {
                verticalMargin = dip(20)
            }

            button("Chọn") {
                onClick {
                    owner.chon()
                }
            }.lparams(matchParent, wrapContent) {
                verticalMargin = dip(20)
            }
            button("Crop") {
                onClick {
                    owner.crop()
                }
            }.lparams(matchParent, wrapContent) {
                verticalMargin = dip(20)
            }

        }
    }
}