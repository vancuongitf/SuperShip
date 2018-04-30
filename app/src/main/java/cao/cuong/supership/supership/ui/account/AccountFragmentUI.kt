package cao.cuong.supership.supership.ui.account

import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
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
                        owner.eventChangePasswordButtonClicked()
                    }
                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, matchParent)
        }
    }
}
