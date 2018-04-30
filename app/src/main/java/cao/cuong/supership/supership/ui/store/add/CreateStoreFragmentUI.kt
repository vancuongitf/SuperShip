package cao.cuong.supership.supership.ui.store.add

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

class CreateStoreFragmentUI : AnkoComponent<CreateStoreFragment> {

    internal lateinit var imgAvatar: ImageView
    internal lateinit var rlAvatar: RelativeLayout
    internal lateinit var edtName: EditText
    internal lateinit var tvAddress: TextView
    internal lateinit var edtPhone: EditText
    internal lateinit var edtEmail: EditText
    internal lateinit var checkBox0: CheckBox
    internal lateinit var checkBox1: CheckBox
    internal lateinit var checkBox2: CheckBox
    internal lateinit var checkBox3: CheckBox
    internal lateinit var checkBox4: CheckBox
    internal lateinit var checkBox5: CheckBox
    internal lateinit var checkBox6: CheckBox
    internal lateinit var tvOpenTime: TextView
    internal lateinit var tvCloseTime: TextView

    override fun createView(ui: AnkoContext<CreateStoreFragment>) = with(ui) {
        scrollView {
            lparams(matchParent, matchParent)
            backgroundResource = R.drawable.bg_login_image
            isClickable = true
            verticalLayout {

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

                        textView(R.string.createStore) {
                            textSizeDimen = R.dimen.storeTitleTextSize
                            textColorResource = R.color.colorBlue
                        }.lparams(0, wrapContent) {
                            weight = 1f
                        }

                        imageView(R.drawable.ic_check_button) {
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

                        checkBox0 = checkBox("T2") {

                        }

                        view { }.lparams(0, wrapContent) {
                            weight = 1f
                        }

                        checkBox1 = checkBox("T3")

                        view { }.lparams(0, wrapContent) {
                            weight = 1f
                        }

                        checkBox2 = checkBox("T4")

                        view { }.lparams(0, wrapContent) {
                            weight = 1f
                        }

                        checkBox3 = checkBox("T5")

                        view { }.lparams(0, wrapContent) {
                            weight = 1f
                        }

                        checkBox4 = checkBox("T6")

                        view { }.lparams(0, wrapContent) {
                            weight = 1f
                        }

                        checkBox5 = checkBox("T7")

                        view { }.lparams(0, wrapContent) {
                            weight = 1f
                        }

                        checkBox6 = checkBox("CN")

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