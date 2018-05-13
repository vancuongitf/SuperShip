package cao.cuong.supership.supership.ui.customer.account

import android.view.ViewGroup
import android.widget.TextView
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

/**
 *
 * @author at-cuongcao.
 */
class BillAddressItemUI : AnkoComponent<ViewGroup> {

    internal lateinit var tvAddress: TextView

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        verticalLayout {
            lparams(matchParent, wrapContent)

            tvAddress = textView {
                textColorResource = R.color.colorBlack
                padding = dimen(R.dimen.accountFragmentLoginPadding)
            }

            view {
                backgroundResource = R.color.colorGray
                alpha = 0.7f
            }.lparams(matchParent, dip(1)) {
                bottomMargin = dip(2)
            }
        }
    }
}
