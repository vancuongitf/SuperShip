package cao.cuong.supership.supership.ui.shipper.account.signup

import android.view.Gravity
import android.widget.EditText
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.*
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 *
 * @author at-cuongcao.
 */
class ShipperSignUpFragmentUI : AnkoComponent<ShipperSignUpFragment> {

    internal lateinit var edtUserName: EditText
    internal lateinit var edtFullName: EditText
    internal lateinit var edtPassword: EditText
    internal lateinit var edtRetypePassword: EditText
    internal lateinit var edtPersonalId: EditText
    internal lateinit var edtBirthday: TextView
    internal lateinit var edtPhoneNumber: EditText
    internal lateinit var edtEmail: EditText
    internal lateinit var edtAddress:EditText

    override fun createView(ui: AnkoContext<ShipperSignUpFragment>) = with(ui) {
        verticalLayout {
            lparams(context.getWidthScreen(), context.getHeightScreen())
            backgroundResource = R.drawable.bg_login_image

            toolbar {

                backgroundColorResource = R.color.colorWhite
                setContentInsetsAbsolute(0, 0)

                linearLayout {

                    gravity = Gravity.CENTER_VERTICAL

                    relativeLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        enableHighLightWhenClicked()
                        onClick {
                            owner.onBackButtonClicked()
                        }

                        imageView(R.drawable.ic_back_button) {
                        }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                            horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }
                    }.lparams(wrapContent, matchParent)

                    textView(R.string.signUpTitle) {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorBlack
                    }.lparams(0, wrapContent) {
                        weight = 1f
                    }

                    relativeLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        enableHighLightWhenClicked()
                        onClick {
                            owner.eventSignUpButtonClicked()
                        }

                        imageView(R.drawable.ic_check_button) {
                        }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                            horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }
                    }.lparams(wrapContent, matchParent)

                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            scrollView {
                verticalLayout {

                    padding = dimen(R.dimen.accountFragmentLoginPadding)

                    commonEditText(R.string.fullName) {
                        edtFullName = editText
                    }.lparams(matchParent, wrapContent) {
                        topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    commonEditText(R.string.userName) {
                        edtUserName = editText
                    }.lparams(matchParent, wrapContent) {
                        topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    commonEditText(R.string.password, true) {
                        edtPassword = editText
                    }.lparams(matchParent, wrapContent) {
                        topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    commonEditText(R.string.retypePassword, true) {
                        edtRetypePassword = editText
                    }.lparams(matchParent, wrapContent) {
                        topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    commonEditText(R.string.personalId) {
                        edtPersonalId = editText
                    }.lparams(matchParent, wrapContent) {
                        topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    commonEditText(R.string.address) {
                        edtAddress = editText
                    }.lparams(matchParent, wrapContent) {
                        topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    commonTextView(R.string.birthDay) {
                        edtBirthday = textView

                        onClick {
                            owner.onBirthDayClicked()
                        }
                    }.lparams(matchParent, wrapContent) {
                        topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    commonEditText(R.string.phoneNumber) {
                        edtPhoneNumber = editText
                    }.lparams(matchParent, wrapContent) {
                        topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    commonEditText(R.string.email) {
                        edtEmail = editText
                    }.lparams(matchParent, wrapContent) {
                        topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }
                }
            }.lparams(matchParent, wrapContent)
        }
    }
}