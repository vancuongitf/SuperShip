package cao.cuong.supership.supership.ui.user.password.change

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.isValidatePassWord
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.sha1
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseBottomSheetDialog
import org.jetbrains.anko.AnkoContext

class ChangePasswordDialog : BaseBottomSheetDialog() {

    internal lateinit var ui: ChangePasswordDialogUI
    internal lateinit var viewModel: ChangePasswordDialogViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = ChangePasswordDialogUI()
        viewModel = ChangePasswordDialogViewModel(context)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onBindViewModel() {
        addDisposables(viewModel.progressDialogStatusObservable
                .observeOnUiThread()
                .subscribe(this::handleUpdateProgressStatus))
    }

    internal fun eventChangePasswordClicked() {
        val oldPass = ui.edtOldPassword.text.toString()
        val newPass = ui.edtNewPassword.text.toString()
        val confirmPass = ui.edtConfirmPassword.text.toString()
        var message = ""
        if (oldPass.isValidatePassWord() && newPass.isValidatePassWord() && confirmPass.isValidatePassWord()) {
            if (newPass == confirmPass) {
                viewModel.changePassword(oldPass.sha1(), newPass.sha1())
                        .observeOnUiThread()
                        .subscribe(this::handleChangePasswordSuccess, this::handleApiError)
            } else {
                message = "Hai mật khẩu mới phải giống nhau."
            }
        } else {
            message = "Mật khẩu gồm 8 - 16 ký tự. Bắt đầu bằng một chữ cái và không chứa ký tự đặc biệt."
        }
        if (message.isNotEmpty()) {
            handleApiError(Throwable(message))
        }
    }

    internal fun eventCancelClicked() {
        dismiss()
    }

    private fun handleChangePasswordSuccess(messageResponse: MessageResponse) {
        context.showOkAlert(R.string.notification, messageResponse.message) {
            dismiss()
        }
    }
}
