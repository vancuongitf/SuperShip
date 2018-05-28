package cao.cuong.supership.supership.ui.customer.store.optional.list

import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.DrinkOption
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.ui.customer.store.optional.adapter.OptionalAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick

class OptionalFragmentUI(options: MutableList<DrinkOption>) : AnkoComponent<OptionalFragment> {

    internal val optionAdapter = OptionalAdapter(options, OptionalAdapter.AdapterType.CREATE_OPTION)

    override fun createView(ui: AnkoContext<OptionalFragment>) = with(ui) {
        verticalLayout {

            lparams(matchParent, matchParent)
            backgroundColorResource = R.color.colorWhite
            isClickable = true

            toolbar {

                backgroundColorResource = R.color.colorGrayLight
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

                    textView(R.string.optionals) {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorBlack
                    }.lparams(0, wrapContent) {
                        weight = 1f
                    }

                    imageView(R.drawable.ic_add_button) {
                        enableHighLightWhenClicked()
                        onClick {
                            owner.addOptionalClicked()
                        }
                    }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                        horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }
                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            view {
                backgroundResource = R.color.colorGrayVeryLight
            }.lparams(matchParent, dip(1)) {
                bottomMargin = dip(2)
            }

            recyclerView {
                id = R.id.recyclerViewAddDrinkOption
                layoutManager = LinearLayoutManager(ctx)
                adapter = optionAdapter
            }
        }
    }
}
