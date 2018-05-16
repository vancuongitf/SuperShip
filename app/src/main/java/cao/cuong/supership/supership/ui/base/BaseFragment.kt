package cao.cuong.supership.supership.ui.base

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.rxevent.UpdateAccountUI
import cao.cuong.supership.supership.data.source.remote.network.ApiException
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.hideKeyBoard
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.customer.account.AccountFragment
import cao.cuong.supership.supership.ui.customer.bill.BillFragment
import cao.cuong.supership.supership.ui.customer.user.UserActivity
import cao.cuong.supership.supership.ui.shipper.bill.checked.CheckedBillFragment
import cao.cuong.supership.supership.ui.shipper.bill.receive.ReceiveBillFragment
import cao.cuong.supership.supership.ui.shipper.info.ShipperInfoFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.okButton
import org.jetbrains.anko.support.v4.alert
import javax.net.ssl.HttpsURLConnection

/**
 *
 *
 */
abstract class BaseFragment : Fragment() {

    protected lateinit var progressDialog: ProgressDialog
    private val subscription: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(context)
        progressDialog.setCancelable(false)
        progressDialog.setMessage(context.getString(R.string.processing))
    }

    override fun onResume() {
        super.onResume()
        onBindViewModel()
    }

    override fun onPause() {
        super.onPause()
        subscription.clear()
    }

    protected fun addDisposables(vararg ds: Disposable) {
        ds.forEach { subscription.add(it) }
    }

    protected fun handleApiError(throwable: Throwable?) {
        if (throwable is ApiException) {
            if (throwable.code == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                if (this !is AccountFragment && this !is BillFragment && this !is CheckedBillFragment && this !is ReceiveBillFragment && this !is ShipperInfoFragment) {
                    alert {
                        this.message = throwable.message ?: "Phiên đăng nhậo đã hết hạn. Vui lòng đăng nhập lại để tiếp tục."
                        this.isCancelable = false
                        this.okButton {
                            val intent = Intent(context, UserActivity::class.java)
                            startActivity(intent)
                            it.dismiss()
                        }

                        this.cancelButton {
                            it.dismiss()
                        }
                    }.show()
                } else {
                    RxBus.publish(UpdateAccountUI())
                }
            } else {
                context.showOkAlert(throwable)
            }
        } else {
            context.showOkAlert(throwable ?: Throwable("Xãy ra lỗi! Vui lòng thử lại sau."))
        }
    }

    protected fun handleUpdateProgressDialogStatus(status: Boolean) {
        if (status) {
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }

    protected fun hideKeyboard() {
        (activity as? BaseActivity)?.hideKeyBoard()
    }

    /**
     * This function is used to define subscription
     */
    abstract fun onBindViewModel()

}
