package cao.cuong.supership.supership.ui.customer.account

import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.commonEditTextWithEditButton
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.ui.base.widget.CommonEditTextWithEditButton
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 *
 * @author at-cuongcao.
 */
class AccountFragmentUI : AnkoComponent<AccountFragment> {

    internal lateinit var llNonLogin: LinearLayout
    internal lateinit var llLogin: LinearLayout
    internal lateinit var edtFullName: CommonEditTextWithEditButton
    internal lateinit var edtPhoneNumber: CommonEditTextWithEditButton
    internal lateinit var edtEmail: CommonEditTextWithEditButton
    internal lateinit var billAddressAdapter: BillAddressAdapter
    internal lateinit var tvReload: TextView

    override fun createView(ui: AnkoContext<AccountFragment>) = with(ui) {

        verticalLayout {
            lparams(matchParent, matchParent)

            toolbar {

                backgroundColorResource = R.color.colorGrayLight
                setContentInsetsAbsolute(0, 0)

                linearLayout {

                    leftPadding = dimen(R.dimen.accountFragmentLoginPadding)

                    gravity = Gravity.CENTER_VERTICAL

                    textView(R.string.personalInfo) {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorBlack
                    }.lparams(0, wrapContent) {
                        weight = 1f
                    }

                    relativeLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        enableHighLightWhenClicked()
                        onClick {
                        }

                        imageView(R.drawable.ic_edit_note) {
                        }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                            horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }
                    }.lparams(wrapContent, matchParent)

                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            relativeLayout {

                backgroundResource = R.color.colorWhite
                padding = dimen(R.dimen.accountFragmentLoginPadding)

                tvReload = textView(R.string.reload) {
                    visibility = View.GONE
                    gravity = Gravity.CENTER
                    backgroundColorResource = R.color.colorBlue
                    enableHighLightWhenClicked()
                    verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)

                    onClick {
                        owner.eventReloadClicked()
                    }
                }.lparams(matchParent, wrapContent) {
                    centerInParent()
                }

                llNonLogin = verticalLayout {
                    lparams(matchParent, matchParent)
                    isClickable = true
                    backgroundColorResource = R.color.colorWhite
                    gravity = Gravity.CENTER
                    visibility = View.GONE

                    textView(R.string.pleaseLogin) {
                        verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                        gravity = Gravity.CENTER_HORIZONTAL
                        textColorResource = R.color.colorBlack
                    }.lparams(matchParent, wrapContent)

                    textView(R.string.login) {
                        gravity = Gravity.CENTER_HORIZONTAL
                        backgroundColorResource = R.color.colorBlue
                        enableHighLightWhenClicked()
                        verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)

                        onClick {
                            owner.eventLoginButtonClick()
                        }
                    }.lparams(matchParent, wrapContent)
                }

                llLogin = verticalLayout {
                    visibility = View.GONE
                    isClickable = true
                    backgroundColorResource = R.color.colorWhite
                    edtFullName = commonEditTextWithEditButton(R.drawable.ic_user, {
                    }) {}.lparams(matchParent, wrapContent) {
                        topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    edtPhoneNumber = commonEditTextWithEditButton(R.drawable.ic_phone, {
                    }) {}.lparams(matchParent, wrapContent) {
                        topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    edtEmail = commonEditTextWithEditButton(R.drawable.ic_email, {
                    }) {}.lparams(matchParent, wrapContent) {
                        topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    view { }.lparams(matchParent, 0) {
                        weight = 1f
                    }

                    textView(R.string.yourStore) {
                        gravity = Gravity.CENTER_HORIZONTAL
                        backgroundColorResource = R.color.colorCyan
                        verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                        enableHighLightWhenClicked()
                        onClick {
                            owner.eventStoreListClicked()
                        }
                    }

                    textView(R.string.changePassword) {
                        gravity = Gravity.CENTER_HORIZONTAL
                        backgroundColorResource = R.color.colorBlue
                        verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                        enableHighLightWhenClicked()
                        onClick {
                            owner.eventChangePasswordButtonClicked()
                        }
                    }.lparams(matchParent, wrapContent) {
                        verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    textView(R.string.logOut) {
                        gravity = Gravity.CENTER_HORIZONTAL
                        backgroundColorResource = R.color.colorGrayLight
                        enableHighLightWhenClicked()
                        verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                        onClick {
                            owner.logOutClick()
                        }
                    }.lparams(matchParent, wrapContent)
                }.lparams(matchParent, matchParent)
            }.lparams(matchParent, matchParent)
        }
    }
}
