package cao.cuong.supership.supership.ui.store.optional.add

import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

class DrinkOptionItemUI : AnkoComponent<ViewGroup> {

    internal lateinit var tvItemName: TextView
    internal lateinit var tvItemPrice: TextView
    internal lateinit var imgDelete: ImageView

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {

            verticalPadding = dimen(R.dimen.addDrinkOptionUIPadding)

            tvItemName = textView {
                id = R.id.addDrinkOptionItemName
                textColorResource = R.color.colorBlack
                gravity = Gravity.CENTER_VERTICAL
            }

            imgDelete = imageView(R.drawable.ic_delete)
                    .lparams(dimen(R.dimen.deleteIconSize), dimen(R.dimen.deleteIconSize)) {
                        alignParentTop()
                        alignParentRight()
                    }

            tvItemPrice = textView {
                textColorResource = R.color.colorBlack
                gravity = Gravity.CENTER_VERTICAL
            }.lparams {
                topMargin = dimen(R.dimen.addDrinkOptionUIPadding)
                bottomOf(R.id.addDrinkOptionItemName)
            }
        }
    }
}