package cao.cuong.supership.supership.ui.customer.store.list

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.StoreInfoExpress
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.ui.customer.home.store.StoreAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class StoreListFragmentUI(private val stores: MutableList<StoreInfoExpress>) : AnkoComponent<StoreListFragment> {

    internal lateinit var tvNonStore: TextView
    internal lateinit var recyclerView: RecyclerView
    internal var storeAdapter = StoreAdapter(stores)
    internal lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun createView(ui: AnkoContext<StoreListFragment>) = with(ui) {

        verticalLayout {
            lparams(matchParent, matchParent)

            toolbar {

                backgroundColorResource = R.color.colorGrayLight
                setContentInsetsAbsolute(0, 0)

                linearLayout {

                    gravity = Gravity.CENTER_VERTICAL

                    relativeLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        enableHighLightWhenClicked()
                        onClick {
                            owner.onBackClicked()
                        }

                        imageView(R.drawable.ic_back_button) {
                        }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                            horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }
                    }.lparams(wrapContent, matchParent)

                    textView(R.string.yourStore) {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorBlue
                    }.lparams(0, wrapContent) {
                        weight = 1f
                    }

                    relativeLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        enableHighLightWhenClicked()
                        onClick {
                            owner.addStoreClicked()
                        }

                        imageView(R.drawable.ic_add_button) {
                        }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                            horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }
                    }.lparams(wrapContent, matchParent)


                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            view {
                backgroundResource = R.color.colorGrayVeryLight
            }.lparams(matchParent, dip(1)) {
                bottomMargin = dip(2)
            }

            swipeRefreshLayout = swipeRefreshLayout {

                verticalLayout {
                    lparams(matchParent, matchParent)

                    tvNonStore = textView(R.string.nonStore) {
                        gravity = Gravity.CENTER
                        visibility = View.GONE
                    }.lparams(matchParent, matchParent)

                    recyclerView = recyclerView {
                        id = R.id.recyclerViewStoreList
                        layoutManager = LinearLayoutManager(ctx)
                        adapter = storeAdapter
                    }.lparams(matchParent, matchParent)
                }

                onRefresh {
                    owner.reloadData()
                }
            }.lparams(matchParent, matchParent)
        }


    }
}