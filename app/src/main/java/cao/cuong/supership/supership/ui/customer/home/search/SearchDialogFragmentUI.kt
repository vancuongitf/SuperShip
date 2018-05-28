package cao.cuong.supership.supership.ui.customer.home.search

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.StoreInfoExpress
import cao.cuong.supership.supership.ui.customer.home.store.StoreAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.textChangedListener

/**
 *
 * @author at-cuongcao.
 */
class SearchDialogFragmentUI(storeInfoExpresses: MutableList<StoreInfoExpress>) : AnkoComponent<SearchDialogFragment> {
    internal lateinit var recyclerViewStores: RecyclerView
    internal val storeAdapter = StoreAdapter(storeInfoExpresses)

    override fun createView(ui: AnkoContext<SearchDialogFragment>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)
            backgroundColorResource = R.color.colorWhite

            toolbar {
                backgroundColorResource = R.color.colorGrayVeryLight
                padding = dimen(R.dimen.toolBarPadding)
                setContentInsetsAbsolute(0, 0)

                linearLayout {
                    backgroundResource = R.drawable.tool_bar_bg
                    gravity = Gravity.CENTER_VERTICAL
                    leftPadding = dimen(R.dimen.toolBarLeftPadding)

                    imageView(R.drawable.ic_search_black_36dp)
                            .lparams(dimen(R.dimen.toolBarSearchIconSize), dimen(R.dimen.toolBarSearchIconSize))

                    editText {
                        backgroundDrawable = null
                        backgroundColorResource = R.color.colorWhite
                        singleLine = true
                        hintResource = R.string.searchTitle

                        textChangedListener {
                            afterTextChanged {
                                owner.handleSearchViewTextChange(it.toString().trim())
                            }
                        }
                        onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                        }
                    }.lparams(matchParent, wrapContent) {
                        leftMargin = dimen(R.dimen.toolBarLeftPadding)
                    }
                }.lparams(matchParent, matchParent)
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            recyclerViewStores = recyclerView {
                id = R.id.storeFragmentRecyclerViewStores
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = storeAdapter
            }.lparams(matchParent, matchParent)
        }
    }
}
