package cao.cuong.supership.supership.ui.store.list

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.StoreInfoExpress
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.ui.home.store.StoreAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick

class StoreListFragmentUI(private val stores: MutableList<StoreInfoExpress>) : AnkoComponent<StoreListFragment> {

    internal lateinit var tvNonStore: TextView
    internal lateinit var recyclerView: RecyclerView
    internal var storeAdapter = StoreAdapter(stores)

    override fun createView(ui: AnkoContext<StoreListFragment>) = with(ui) {

        verticalLayout {
            lparams(matchParent, matchParent)

            toolbar {

                setContentInsetsAbsolute(0, 0)

                linearLayout {

                    gravity = Gravity.CENTER_VERTICAL

                    imageView(R.drawable.ic_back_button) {
                        enableHighLightWhenClicked()
                        onClick {
                            owner.onBackClicked()
                        }
                    }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                        horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    textView(R.string.yourStore) {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorBlue
                    }.lparams(0, wrapContent) {
                        weight = 1f
                    }

                    imageView(R.drawable.ic_add_button) {
                        enableHighLightWhenClicked()
                        onClick {
                            owner.addStoreClicked()
                        }
                    }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                        horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }
                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            view {
                backgroundResource = R.color.colorGray
                alpha = 0.7f
            }.lparams(matchParent, dip(1)) {
                bottomMargin = dip(2)
            }

            tvNonStore = textView(R.string.nonStore) {
                gravity = Gravity.CENTER
                visibility = View.GONE
            }.lparams(matchParent, 0) {
                weight = 1f
            }

            recyclerView = recyclerView {
                id = R.id.recyclerViewStoreList
                layoutManager = LinearLayoutManager(ctx)
                adapter = storeAdapter
            }.lparams(matchParent, 0) {
                weight = 1f
            }
        }


    }
}