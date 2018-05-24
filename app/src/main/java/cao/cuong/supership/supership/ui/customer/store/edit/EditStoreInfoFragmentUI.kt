package cao.cuong.supership.supership.ui.customer.store.edit

import android.view.Gravity
import android.view.View
import android.widget.*
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.commonEditText
import cao.cuong.supership.supership.extension.commonTextView
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.extension.getWidthScreen
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.sdk25.coroutines.onClick

class EditStoreInfoFragmentUI : AnkoComponent<EditStoreInfoFragment> {

    internal val checkBoxlist = mutableListOf<CheckBox>()
    internal lateinit var imgAvatar: ImageView
    internal lateinit var rlAvatar: RelativeLayout
    internal lateinit var edtName: EditText
    internal lateinit var tvAddress: TextView
    internal lateinit var edtPhone: EditText
    internal lateinit var edtEmail: EditText
    internal lateinit var tvOpenTime: TextView
    internal lateinit var tvCloseTime: TextView

    override fun createView(ui: AnkoContext<EditStoreInfoFragment>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)
            backgroundColorResource = R.color.colorWhite
            isClickable = true

            toolbar {

                backgroundColorResource = R.color.colorWhite
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

                    textView(R.string.createStore) {
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

                        imageView(R.drawable.ic_check_button) {
                        }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                            horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }
                    }.lparams(wrapContent, matchParent)

                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            view {
                backgroundResource = R.color.colorGray
                alpha = 0.7f
            }.lparams(matchParent, dip(1)) {
                bottomMargin = dip(2)
            }
            scrollView {


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

                    commonEditText(R.string.storeName) {
                        edtName = editText
                    }.lparams(matchParent, wrapContent) {
                        topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    commonTextView(R.string.address) {
                        tvAddress = textView
                        tvAddress.isEnabled = false
                        onClick {
                            owner.onChooseAddressClicked()
                        }
                    }.lparams(matchParent, wrapContent) {
                        topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    commonEditText(R.string.phoneNumber) {
                        edtPhone = editText
                    }.lparams(matchParent, wrapContent) {
                        topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    commonEditText(R.string.email) {
                        edtEmail = editText
                    }.lparams(matchParent, wrapContent) {
                        topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    linearLayout {

                        backgroundColorResource = R.color.colorWhite

                        checkBox("T2") {
                            checkBoxlist.add(this)
                        }

                        view { }.lparams(0, wrapContent) {
                            weight = 1f
                        }

                        checkBox("T3") {
                            checkBoxlist.add(this)
                        }

                        view { }.lparams(0, wrapContent) {
                            weight = 1f
                        }

                        checkBox("T4") {
                            checkBoxlist.add(this)
                        }

                        view { }.lparams(0, wrapContent) {
                            weight = 1f
                        }

                        checkBox("T5") {
                            checkBoxlist.add(this)
                        }

                        view { }.lparams(0, wrapContent) {
                            weight = 1f
                        }

                        checkBox("T6") {
                            checkBoxlist.add(this)
                        }

                        view { }.lparams(0, wrapContent) {
                            weight = 1f
                        }

                        checkBox("T7") {
                            checkBoxlist.add(this)
                        }

                        view { }.lparams(0, wrapContent) {
                            weight = 1f
                        }

                        checkBox("CN") {
                            checkBoxlist.add(this)
                        }

                        view { }.lparams(0, wrapContent) {
                            weight = 1f
                        }

                    }.lparams(matchParent, wrapContent) {
                        verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    linearLayout {

                        gravity = Gravity.CENTER_VERTICAL

                        textView(R.string.openTime) {
                            gravity = Gravity.CENTER_HORIZONTAL
                            backgroundColorResource = R.color.colorBlue
                            textColorResource = R.color.colorWhite
                            padding = dimen(R.dimen.accountFragmentLoginPadding)
                            enableHighLightWhenClicked()
                            onClick {
                                owner.chooseOpenTime()
                            }
                        }.lparams(ctx.getWidthScreen() / 2, wrapContent) {
                            verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }

                        tvOpenTime = textView {
                            textColorResource = R.color.colorWhite
                        }.lparams {
                            leftMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }
                    }

                    linearLayout {

                        gravity = Gravity.CENTER_VERTICAL

                        textView(R.string.closeTime) {
                            gravity = Gravity.CENTER_HORIZONTAL
                            backgroundColorResource = R.color.colorBlue
                            textColorResource = R.color.colorWhite
                            padding = dimen(R.dimen.accountFragmentLoginPadding)
                            enableHighLightWhenClicked()
                            onClick {
                                owner.chooseCloseTime()
                            }
                        }.lparams(ctx.getWidthScreen() / 2, wrapContent) {
                            verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }

                        tvCloseTime = textView {
                            textColorResource = R.color.colorWhite
                        }.lparams {
                            leftMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }
                    }

                }.lparams(matchParent, wrapContent)

            }.lparams(matchParent, wrapContent)
        }
    }
}