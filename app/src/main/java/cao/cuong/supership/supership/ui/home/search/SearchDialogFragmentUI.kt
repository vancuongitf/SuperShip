package cao.cuong.supership.supership.ui.home.search

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.StoreInfoExpress
import cao.cuong.supership.supership.ui.home.store.StoreAdapter
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

            /*
             val phone = mutableListOf<String>("vancuong@gmail.com",
                "van&cuong@asiantech",
                "van.cuong@yahoo",
                "2an@_c@gmail.com",
                "asd asd@gmail.com")
        phone.forEach {
            val pattern = Pattern.compile(Patterns.EMAIL_ADDRESS.pattern())
            if (pattern.toRegex().matches(it)) {

            }
            Log.i("tag11xx", pattern.pattern())
            Log.i("tag11xx", pattern.toRegex().pattern)

            Log.i("tag11", it)
        }
             */

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
                            Log.i("tag11", hasFocus.toString())
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
                storeAdapter.onItemClicked = {
                    owner.onItemClick(it)
                }
            }.lparams(matchParent, matchParent)
        }
    }
}
