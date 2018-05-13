package cao.cuong.supership.supership.ui.customer.store.optional.add

import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.view.Gravity
import android.widget.CheckBox
import android.widget.EditText
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.source.remote.request.DrinkOptionItemBody
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick

class AddDrinkOptionFragmentUI(items: MutableList<DrinkOptionItemBody>) : AnkoComponent<AddDrinkOptionFragment> {

    internal lateinit var edtOptionName: EditText
    internal lateinit var edtDrinkOptionItemName: EditText
    internal lateinit var edtDrinkOptionItemPrice: EditText
    internal lateinit var checkBoxMultiChoose: CheckBox
    internal val itemAdapter = DrinkOptionAdapter(items)

    override fun createView(ui: AnkoContext<AddDrinkOptionFragment>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)
            backgroundColorResource = R.color.colorWhite
            isClickable = true

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
                        horizontalMargin = dimen(R.dimen.addDrinkOptionUIPadding)
                    }

                    textView(R.string.addDrinkOptional) {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorBlack
                    }.lparams(0, wrapContent) {
                        weight = 1f
                    }

                    imageView(R.drawable.ic_check_button) {
                        enableHighLightWhenClicked()
                        onClick {
                            owner.addOptionalClicked()
                        }
                    }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                        horizontalMargin = dimen(R.dimen.addDrinkOptionUIPadding)
                    }
                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            view {
                backgroundResource = R.color.colorGrayVeryLight
            }.lparams(matchParent, dip(1)) {
                bottomMargin = dip(2)
            }

            verticalLayout {
                padding = dimen(R.dimen.addDrinkOptionUIPadding)

                edtOptionName = editText {
                    backgroundColorResource = R.color.colorGrayVeryVeryLight
                    textColorResource = R.color.colorBlack
                    verticalPadding = dip(5)
                    singleLine = true
                    hint = ctx.getString(R.string.optionName)
                    horizontalPadding = dimen(R.dimen.addDrinkOptionUIPadding)
                }.lparams(matchParent, wrapContent)

                checkBoxMultiChoose = checkBox(R.string.multiChoose) {

                }.lparams {
                    verticalMargin = dimen(R.dimen.drinkItemUIPadding)
                    leftMargin = dip(-5)
                }

                edtDrinkOptionItemName = editText {
                    backgroundColorResource = R.color.colorGrayVeryVeryLight
                    textColorResource = R.color.colorBlack
                    verticalPadding = dip(5)
                    singleLine = true
                    hint = ctx.getString(R.string.optionName)
                    horizontalPadding = dimen(R.dimen.addDrinkOptionUIPadding)
                }.lparams(matchParent, wrapContent)

                linearLayout {

                    gravity = Gravity.CENTER_VERTICAL

                    edtDrinkOptionItemPrice = editText {
                        backgroundColorResource = R.color.colorGrayVeryVeryLight
                        textColorResource = R.color.colorBlack
                        verticalPadding = dip(5)
                        singleLine = true
                        inputType = InputType.TYPE_CLASS_NUMBER
                        hint = ctx.getString(R.string.price)
                        horizontalPadding = dimen(R.dimen.addDrinkOptionUIPadding)
                    }.lparams(0, wrapContent) {
                        weight = 1f
                    }

                    imageView(R.drawable.ic_check_button) {
                        enableHighLightWhenClicked()
                        onClick {
                            owner.addItemOptionClick()
                        }
                    }.lparams(dimen(R.dimen.addDrinkOptionItemHight), dimen(R.dimen.addDrinkOptionItemHight)) {
                        leftMargin = dimen(R.dimen.drinkItemUIPadding)
                    }

                }.lparams(matchParent, wrapContent) {
                    verticalMargin = dimen(R.dimen.drinkItemUIPadding)
                }

                textView(R.string.optionList) {
                    gravity = Gravity.CENTER_VERTICAL
                }.lparams(matchParent, dimen(R.dimen.addDrinkOptionItemHight))

                recyclerView {
                    id = R.id.recyclerOptionItemList
                    layoutManager = LinearLayoutManager(ctx)
                    adapter = itemAdapter
                }
            }.lparams(matchParent, matchParent)
        }
    }
}
