package cao.cuong.supership.supership.ui.user.password.change

import android.view.Gravity
import android.widget.EditText
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.commonEditText
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ChangePasswordDialogUI : AnkoComponent<ChangePasswordDialog> {

    internal lateinit var edtOldPassword: EditText
    internal lateinit var edtNewPassword: EditText
    internal lateinit var edtConfirmPassword: EditText

    override fun createView(ui: AnkoContext<ChangePasswordDialog>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)
            backgroundResource = R.drawable.bg_login_image
            padding = dimen(R.dimen.accountFragmentLoginPadding)

            view {

            }.lparams(matchParent, 0) {
                weight = 1f
            }

            commonEditText(R.string.oldPassword, true) {
                edtOldPassword = editText
            }.lparams(matchParent, wrapContent)

            commonEditText(R.string.newPassword, true) {
                edtNewPassword = editText
            }.lparams(matchParent, wrapContent)

            commonEditText(R.string.retypeNewPassword, true) {
                edtConfirmPassword = editText
            }.lparams(matchParent, wrapContent)


            textView(R.string.changePassword) {
                gravity = Gravity.CENTER_HORIZONTAL
                backgroundColorResource = R.color.colorBlue
                textColorResource = R.color.colorBlack
                enableHighLightWhenClicked()
                verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                onClick {
                    owner.eventChangePasswordClicked()
                }
            }.lparams(matchParent, wrapContent) {
                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            textView(R.string.cancle) {
                gravity = Gravity.CENTER_HORIZONTAL
                backgroundColorResource = R.color.colorGrayLight
                textColorResource = R.color.colorBlack
                enableHighLightWhenClicked()
                verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                onClick {
                    owner.eventCancelClicked()
                }
            }.lparams(matchParent, wrapContent) {
                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            view {

            }.lparams(matchParent, 0) {
                weight = 1f
            }
        }
    }
}