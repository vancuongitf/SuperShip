package cao.cuong.supership.supership.ui.account

import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.widget.LinearLayout
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.commonEditTextWithEditButton
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.ui.base.widget.CommonEditTextWithEditButton
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
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

    override fun createView(ui: AnkoContext<AccountFragment>) = with(ui) {
        relativeLayout {

            backgroundResource = R.color.colorWhite
            padding = dimen(R.dimen.accountFragmentLoginPadding)

            llNonLogin = verticalLayout {
                lparams(matchParent, matchParent)
                gravity = Gravity.CENTER

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

                edtFullName = commonEditTextWithEditButton(R.drawable.ic_user, {
                    owner.eventChangeFullNameClick()
                }) {}.lparams(matchParent, wrapContent) {
                    topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }

                edtPhoneNumber = commonEditTextWithEditButton(R.drawable.ic_phone, {
                    owner.eventChangePhoneNumberClick()
                }) {}.lparams(matchParent, wrapContent) {
                    topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }

                edtEmail = commonEditTextWithEditButton(R.drawable.ic_email, {
                    owner.eventChangeEmailClick()
                }) {}.lparams(matchParent, wrapContent) {
                    topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }

                linearLayout {

                    gravity = Gravity.CENTER_VERTICAL

                    textView(R.string.billAddresses) {
                        textColorResource = R.color.colorBlack
                    }.lparams(0, wrapContent) {
                        weight = 1f
                    }

                    imageView {
                        backgroundResource = R.drawable.ic_add

                        onClick {
                        }
                    }.lparams(dimen(R.dimen.commonEditTextHieght), dimen(R.dimen.commonEditTextHieght)) {
                        margin = dimen(R.dimen.accountFragmentLoginPadding)
                    }
                }.lparams(matchParent, wrapContent)

                view {
                    backgroundResource = R.color.colorRed
                    alpha = 0.7f
                }.lparams(matchParent, dip(1)) {
                    bottomMargin = dip(2)
                }

                recyclerView {
                    id = R.id.accountFragmentRecyclerViewBillAddress
                    layoutManager = LinearLayoutManager(ctx)
                    adapter = billAddressAdapter
                }.lparams(matchParent, 0) {
                    weight = 1f
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
                    backgroundColorResource = R.color.colorCyan
                    enableHighLightWhenClicked()
                    verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                    onClick {
                        owner.eventChangePasswordButtonClicked()
                    }
                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, matchParent)
        }
    }
}
