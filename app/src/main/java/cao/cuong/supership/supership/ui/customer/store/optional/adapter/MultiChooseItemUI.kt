package cao.cuong.supership.supership.ui.customer.store.optional.adapter

import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.commonCheckBox
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.ui.base.widget.CommonCheckBox
import org.jetbrains.anko.*

class MultiChooseItemUI : AnkoComponent<ViewGroup> {

    companion object {
        internal const val MAX_OPTION_ITEM = 10
    }

    internal lateinit var tvOptionName: TextView
    internal lateinit var imgEdit: ImageView
    internal lateinit var imgDelete: ImageView
    internal lateinit var imgApply: ImageView
    internal lateinit var imgClear: ImageView
    internal val checkBoxes = mutableListOf<CommonCheckBox>()
    internal var onCheckedChange: (index: Int, isChecked: Boolean) -> Unit = { _, _ -> }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        verticalLayout {
            lparams(matchParent, wrapContent)
            horizontalPadding = dimen(R.dimen.addDrinkOptionUIPadding)

            linearLayout {

                gravity = Gravity.CENTER_VERTICAL

                tvOptionName = textView {
                    verticalPadding = dimen(R.dimen.drinkOptionItemPadding)
                    textColorResource = R.color.colorBlack
                    setTypeface(null, Typeface.BOLD)
                }.lparams(0, wrapContent) {
                    weight = 1f
                }

                imgEdit = imageView(R.drawable.ic_mode_edit) {
                    id = R.id.imgEditDrinkOption
                    enableHighLightWhenClicked()
                }.lparams(dimen(R.dimen.drinkOptionItemButton), dimen(R.dimen.drinkOptionItemButton)) {
                    rightMargin = dimen(R.dimen.addDrinkOptionUIPadding)
                }

                imgDelete = imageView(R.drawable.ic_trash) {
                    id = R.id.imgDeleteDrinkOption
                    enableHighLightWhenClicked()
                }.lparams(dimen(R.dimen.drinkOptionItemButton), dimen(R.dimen.drinkOptionItemButton))

                imgApply = imageView(R.drawable.ic_check_button) {
                    id = R.id.imgApplyDrinkOption
                    enableHighLightWhenClicked()
                }.lparams(dimen(R.dimen.drinkOptionItemButton), dimen(R.dimen.drinkOptionItemButton))

                imgClear = imageView(R.drawable.ic_delete) {
                    id = R.id.imgCancelOption
                    enableHighLightWhenClicked()
                }.lparams(dimen(R.dimen.drinkOptionItemButton), dimen(R.dimen.drinkOptionItemButton))

            }.lparams(matchParent, wrapContent)

            view {
                backgroundResource = R.color.colorGrayVeryLight
            }.lparams(matchParent, dip(1))

            for (i in 0 until MAX_OPTION_ITEM) {
                commonCheckBox {
                    checkBoxes.add(this)
                    checkBox.setOnCheckedChangeListener { _, isChecked ->
                        onCheckedChange(i, isChecked)
                    }
                }.lparams(matchParent, wrapContent)
            }

            view {
                backgroundResource = R.color.colorGrayVeryLight
            }.lparams(matchParent, dip(1))
        }
    }
}
