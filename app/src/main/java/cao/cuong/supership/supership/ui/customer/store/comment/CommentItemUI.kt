package cao.cuong.supership.supership.ui.customer.store.comment

import android.graphics.Typeface
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.TextView
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*

class CommentItemUI : AnkoComponent<ViewGroup> {

    internal lateinit var tvUserName: TextView
    internal lateinit var tvCommentTime: TextView
    internal lateinit var tvComment: TextView

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        verticalLayout {
            lparams(matchParent, wrapContent)
            padding = dimen(R.dimen.accountFragmentLoginPadding)

            tvUserName = textView {
                typeface = Typeface.DEFAULT_BOLD
                textSizeDimen = R.dimen.ratingItemTextSize
                textColorResource = R.color.colorBlue
                singleLine = true
                ellipsize = TextUtils.TruncateAt.END
            }

            tvCommentTime = textView {
                singleLine = true
            }.lparams {
                topMargin = dip(5)
            }

            tvComment = textView {
                textColorResource = R.color.colorBlack
            }
        }
    }
}
