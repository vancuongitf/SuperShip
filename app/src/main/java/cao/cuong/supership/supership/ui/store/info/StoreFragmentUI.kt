package cao.cuong.supership.supership.ui.store.info

import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 *
 * @author at-cuongcao.
 */
class StoreFragmentUI : AnkoComponent<StoreFragment> {

    internal lateinit var tvStoreName: TextView

    override fun createView(ui: AnkoContext<StoreFragment>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)

            toolbar {

                setContentInsetsAbsolute(0, 0)

                linearLayout {

                    gravity = Gravity.CENTER_VERTICAL

                    imageView(R.drawable.ic_back) {
                        enableHighLightWhenClicked()
                        onClick {
                            owner.onBackClicked()
                        }
                    }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)){
                        horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    tvStoreName = textView {
                        text = "Trà Đào ABC"
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorBlue
                    }
                }

            }.lparams(matchParent, dimen(R.dimen.toolBarHeight)) {
            }

            view {
                backgroundResource = R.color.colorGray
                alpha = 0.7f
            }.lparams(matchParent, dip(1)) {
                bottomMargin = dip(2)
            }

            recyclerView {
                id = R.id.recyclerViewStoreInfo
                layoutManager = LinearLayoutManager(ctx)
                adapter = StoreInfoAdapter()
            }
        }
    }
}
