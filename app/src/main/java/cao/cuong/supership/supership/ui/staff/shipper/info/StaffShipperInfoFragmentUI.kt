package cao.cuong.supership.supership.ui.staff.shipper.info

import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.commonEditTextWithEditButton
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.ui.base.widget.CommonEditTextWithEditButton
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.sdk25.coroutines.onClick

class StaffShipperInfoFragmentUI : AnkoComponent<StaffShipperInfoFragment> {

    internal lateinit var rlChangeStatusButton: RelativeLayout
    internal lateinit var rlBannedButton: RelativeLayout
    internal lateinit var imgChangeStatus: ImageView
    internal lateinit var edtId: CommonEditTextWithEditButton
    internal lateinit var edtFullName: CommonEditTextWithEditButton
    internal lateinit var edtPersonalId: CommonEditTextWithEditButton
    internal lateinit var edtBirthDay: CommonEditTextWithEditButton
    internal lateinit var edtDeposit: CommonEditTextWithEditButton
    internal lateinit var edtAddress: CommonEditTextWithEditButton
    internal lateinit var edtPhoneNumber: CommonEditTextWithEditButton
    internal lateinit var edtEmail: CommonEditTextWithEditButton

    override fun createView(ui: AnkoContext<StaffShipperInfoFragment>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)

            toolbar {

                backgroundColorResource = R.color.colorGrayLight
                setContentInsetsAbsolute(0, 0)

                linearLayout {

                    leftPadding = dimen(R.dimen.accountFragmentLoginPadding)

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

                    textView(R.string.shipperInfo) {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorBlack
                    }.lparams(0, wrapContent) {
                        weight = 1f
                    }

                    rlBannedButton = relativeLayout {
                        visibility = View.GONE

                        relativeLayout {
                            gravity = Gravity.CENTER_VERTICAL

                            enableHighLightWhenClicked()
                            onClick {
                                owner.eventBannedButtonClicked()
                            }

                            imageView(R.drawable.ic_ban) {
                            }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                            }
                        }.lparams(wrapContent, matchParent)
                    }

                    rlChangeStatusButton = relativeLayout {
                        visibility = View.GONE
                        gravity = Gravity.CENTER_VERTICAL

                        enableHighLightWhenClicked()
                        onClick {
                            owner.eventChangeStatusClicked()
                        }

                        imgChangeStatus = imageView(R.drawable.ic_ban) {
                        }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                            horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }
                    }.lparams(wrapContent, matchParent)

                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            verticalLayout {
                isClickable = true
                backgroundColorResource = R.color.colorWhite
                padding = dimen(R.dimen.accountFragmentLoginPadding)

                edtId = commonEditTextWithEditButton(R.drawable.ic_user_id, {
                }) {}.lparams(matchParent, wrapContent) {
                    topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }

                edtFullName = commonEditTextWithEditButton(R.drawable.ic_user, {
                }) {}.lparams(matchParent, wrapContent) {
                    topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }

                edtPersonalId = commonEditTextWithEditButton(R.drawable.ic_personal_id, {
                }) {}.lparams(matchParent, wrapContent) {
                    topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }

                edtBirthDay = commonEditTextWithEditButton(R.drawable.ic_callendar, {
                }) {}.lparams(matchParent, wrapContent) {
                    topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }

                edtDeposit = commonEditTextWithEditButton(R.drawable.ic_money, {
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

                view { }.lparams(matchParent, 0) {
                    weight = 1f
                }

            }.lparams(matchParent, matchParent)
        }
    }
}
