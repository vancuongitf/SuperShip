package cao.cuong.supership.supership.ui.customer.store.comment.rating

import android.support.v7.widget.LinearLayoutManager
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Rating
import cao.cuong.supership.supership.extension.getWidthScreen
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class StoreRatingDialogUI(ratings: List<Rating>) : AnkoComponent<StoreRatingDialog> {

    internal val ratingAdapter = StarRatingAdapter(ratings)

    override fun createView(ui: AnkoContext<StoreRatingDialog>) = with(ui) {
        verticalLayout {
            lparams(context.getWidthScreen(), wrapContent)
            padding = dimen(R.dimen.accountFragmentLoginPadding)
            backgroundColorResource = R.color.colorWhite

            textView(R.string.ratingMessage) {
                textSizeDimen = R.dimen.ratingItemTextSize
                textColorResource = R.color.colorBlack
                horizontalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                bottomPadding = dimen(R.dimen.accountFragmentLoginPadding)
            }

            recyclerView {
                id = R.id.recyclerViewRatings
                layoutManager = LinearLayoutManager(context)
                adapter = ratingAdapter
            }.lparams(matchParent, wrapContent)
        }
    }
}
