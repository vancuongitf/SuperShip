package cao.cuong.supership.supership.ui.customer.store.comment.rating

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Rating
import cao.cuong.supership.supership.data.model.rxevent.UpdateCommentUI
import cao.cuong.supership.supership.data.source.remote.network.ApiException
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.customer.user.UserActivity
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.okButton
import javax.net.ssl.HttpsURLConnection

class StoreRatingDialog(context: Context, private val storeId: Long, private val ratings: MutableList<Rating>) : BottomSheetDialog(context) {

    private lateinit var ui: StoreRatingDialogUI
    private lateinit var viewModel: StoreRatingDialogViewModel
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(context)
        progressDialog.setCancelable(false)
        progressDialog.setMessage(context.getString(R.string.processing))
        viewModel = StoreRatingDialogViewModel(context)
        ui = StoreRatingDialogUI(ratings)
        ui.ratingAdapter.onItemClicked = this::adapterItemClick
        viewModel.updateProgressDialogObservable
                .observeOnUiThread()
                .subscribe {
                    if (it) {
                        progressDialog.show()
                    } else {
                        progressDialog.dismiss()
                    }
                }
        setContentView(ui.createView(AnkoContext.Companion.create(context, this)))
    }

    private fun adapterItemClick(rating: Rating) {
        if (viewModel.isLogin()) {
            viewModel.storeRating(storeId, rating.value)
                    .observeOnUiThread()
                    .subscribe(this::handleRatingSuccess, this::handleRatingFail)
        } else {
            handleRatingFail(ApiException(401, "Bạn chưa đăng nhập. Vui lòng đăng nhập để tiếp tục."))
        }
    }

    private fun handleRatingSuccess(ratings: List<Rating>) {
        this.ratings.clear()
        this.ratings.addAll(ratings)
        ui.ratingAdapter.notifyDataSetChanged()
        RxBus.publish(UpdateCommentUI())
    }

    private fun handleRatingFail(throwable: Throwable) {
        if (throwable is ApiException) {
            if (throwable.code == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                context.alert {
                    this.message = throwable.message ?: "Phiên đăng nhậo đã hết hạn. Vui lòng đăng nhập lại để tiếp tục."
                    this.isCancelable = false
                    this.okButton {
                        val intent = Intent(context, UserActivity::class.java)
                        context.startActivity(intent)
                        it.dismiss()
                    }

                    this.cancelButton {
                        it.dismiss()
                    }
                }.show()
            } else {
                context.showOkAlert(throwable)
            }
        } else {
            context.showOkAlert(throwable ?: Throwable("Xãy ra lỗi! Vui lòng thử lại sau."))
        }
    }
}
