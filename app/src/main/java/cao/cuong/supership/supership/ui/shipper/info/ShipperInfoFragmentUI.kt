package cao.cuong.supership.supership.ui.shipper.info

import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.commonEditTextWithEditButton
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.extension.enableHighLightWhenClickedForListItem
import cao.cuong.supership.supership.ui.base.widget.CommonEditTextWithEditButton
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.sdk25.coroutines.onClick

class ShipperInfoFragmentUI : AnkoComponent<ShipperInfoFragment> {
    internal lateinit var llNonLogin: LinearLayout
    internal lateinit var llLogin: ScrollView
    internal lateinit var edtFullName: CommonEditTextWithEditButton
    internal lateinit var edtPhoneNumber: CommonEditTextWithEditButton
    internal lateinit var edtEmail: CommonEditTextWithEditButton
    internal lateinit var tvReload: TextView
    internal lateinit var edtBirthDay: CommonEditTextWithEditButton
    internal lateinit var edtAddress: CommonEditTextWithEditButton
    internal lateinit var edtDeposit: CommonEditTextWithEditButton
    internal lateinit var rlEditInfo: RelativeLayout
    internal lateinit var rlSubmitNewInfo: RelativeLayout

    override fun createView(ui: AnkoContext<ShipperInfoFragment>) = with(ui) {

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

                    rlEditInfo = relativeLayout {
                        visibility = View.GONE

                        relativeLayout {
                            gravity = Gravity.CENTER_VERTICAL

                            enableHighLightWhenClicked()
                            onClick {
                                owner.onEditInfoClicked()
                            }

                            imageView(R.drawable.ic_edit_note) {
                            }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                            }
                        }.lparams(wrapContent, matchParent)
                    }

                    rlSubmitNewInfo = relativeLayout {
                        visibility = View.GONE

                        relativeLayout {
                            gravity = Gravity.CENTER_VERTICAL

                            enableHighLightWhenClicked()
                            onClick {
                                owner.onSubmitNewInfoClicked()
                            }

                            imageView(R.drawable.ic_check_button) {
                            }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                            }
                        }.lparams(wrapContent, matchParent)
                    }


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

                llLogin = scrollView {

                    visibility = View.GONE

                    verticalLayout {
                        lparams(matchParent, matchParent)
                        isClickable = true
                        backgroundColorResource = R.color.colorWhite
                        edtFullName = commonEditTextWithEditButton(R.drawable.ic_user, {
                        }) {}.lparams(matchParent, wrapContent) {
                            topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }

                        edtBirthDay = commonEditTextWithEditButton(R.drawable.ic_date, {
                        }) {}.lparams(matchParent, wrapContent) {
                            topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }

                        edtAddress = commonEditTextWithEditButton(R.drawable.ic_address, {
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

                        edtDeposit = commonEditTextWithEditButton(R.drawable.ic_money, {
                        }) {}.lparams(matchParent, wrapContent) {
                            topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }

                        view { }.lparams(matchParent, 0) {
                            weight = 1f
                        }

                        textView(R.string.cash) {
                            gravity = Gravity.CENTER_HORIZONTAL
                            backgroundColorResource = R.color.colorCyan
                            verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                            enableHighLightWhenClickedForListItem()
                            onClick {
                                owner.eventCashClicked()
                            }
                        }.lparams(matchParent, wrapContent) {
                            topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }

                        textView(R.string.changePassword) {
                            gravity = Gravity.CENTER_HORIZONTAL
                            backgroundColorResource = R.color.colorBlue
                            verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                            enableHighLightWhenClickedForListItem()
                            onClick {
                                owner.eventChangePasswordButtonClicked()
                            }
                        }.lparams(matchParent, wrapContent) {
                            verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }

                        textView(R.string.logOut) {
                            gravity = Gravity.CENTER_HORIZONTAL
                            backgroundColorResource = R.color.colorGrayLight
                            enableHighLightWhenClickedForListItem()
                            verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                            onClick {
                                owner.logOutClick()
                            }
                        }.lparams(matchParent, wrapContent)
                    }
                }.lparams(matchParent, matchParent)
            }.lparams(matchParent, matchParent)
        }
    }
}
