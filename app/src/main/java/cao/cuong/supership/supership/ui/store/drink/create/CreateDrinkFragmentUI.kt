package cao.cuong.supership.supership.ui.store.drink.create

import android.graphics.Typeface
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.DrinkOption
import cao.cuong.supership.supership.extension.commonEditText
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.extension.getWidthScreen
import cao.cuong.supership.supership.ui.store.optional.adapter.OptionalAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick

class CreateDrinkFragmentUI(options: MutableList<DrinkOption>) : AnkoComponent<CreateDrinkFragment> {
    internal lateinit var imgAvatar: ImageView
    internal lateinit var rlAvatar: RelativeLayout
    internal lateinit var edtName: EditText
    internal lateinit var edtPrice: EditText
    internal val optionsAdapter = OptionalAdapter(options, OptionalAdapter.AdapterType.CREATE_DRINK)

    override fun createView(ui: AnkoContext<CreateDrinkFragment>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)
            backgroundResource = R.drawable.bg_login_image
            isClickable = true

                toolbar {

                    backgroundColorResource = R.color.colorWhite
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

                        textView(R.string.createDrink) {
                            textSizeDimen = R.dimen.storeTitleTextSize
                            setTypeface(null, Typeface.BOLD)
                            textColorResource = R.color.colorBlack
                        }.lparams(0, wrapContent) {
                            weight = 1f
                        }

                        imageView(R.drawable.ic_check_button) {
                            enableHighLightWhenClicked()
                            onClick {
                                owner.addDrinkClicked()
                            }
                        }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                            horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }
                    }.lparams(matchParent, wrapContent)
                }.lparams(matchParent, dimen(R.dimen.toolBarHeight))
            scrollView {

                verticalLayout {

                    view {
                        backgroundResource = R.color.colorGray
                        alpha = 0.7f
                    }.lparams(matchParent, dip(1)) {
                        bottomMargin = dip(2)
                    }

                    verticalLayout {
                        padding = dimen(R.dimen.accountFragmentLoginPadding)

                        textView(R.string.chooseAvatar) {
                            gravity = Gravity.CENTER_HORIZONTAL
                            backgroundColorResource = R.color.colorBlue
                            textColorResource = R.color.colorWhite
                            padding = dimen(R.dimen.accountFragmentLoginPadding)
                            enableHighLightWhenClicked()
                            onClick {
                                owner.chooseAvatar()
                            }
                        }.lparams(ctx.getWidthScreen() / 2, wrapContent) {
                            verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }

                        rlAvatar = relativeLayout {

                            padding = dimen(R.dimen.avatarBoder)
                            backgroundColorResource = R.color.colorWhite
                            visibility = View.GONE

                            imgAvatar = imageView {

                            }.lparams(ctx.getWidthScreen() / 2, ctx.getWidthScreen() / 2)
                        }.lparams(wrapContent, wrapContent)

                        commonEditText(R.string.drinkName) {
                            edtName = editText
                        }.lparams(matchParent, wrapContent) {
                            topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }

                        commonEditText(R.string.price) {
                            edtPrice = editText
                            edtPrice.inputType = InputType.TYPE_CLASS_NUMBER
                        }.lparams(matchParent, wrapContent) {
                            topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }

                        textView(R.string.optionList) {
                            textColorResource = R.color.colorWhite
                            bottomPadding = dip(2)
                        }.lparams(matchParent, wrapContent) {
                            topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }

                        view {
                            backgroundResource = R.color.colorRed
                            alpha = 0.7f
                        }.lparams(matchParent, dip(1)) {
                            bottomMargin = dip(2)
                        }

                        recyclerView {
                            id = R.id.createDrinkDrinkOptions
                            layoutManager = LinearLayoutManager(ctx)
                            backgroundColorResource = R.color.colorWhiteLight
                            adapter = optionsAdapter
                        }
                    }.lparams(matchParent, wrapContent)
                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, matchParent)
        }
    }
}
