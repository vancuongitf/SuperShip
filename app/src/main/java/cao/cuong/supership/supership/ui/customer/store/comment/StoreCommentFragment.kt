package cao.cuong.supership.supership.ui.customer.store.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.rxevent.UpdateCommentUI
import cao.cuong.supership.supership.data.source.remote.network.ApiException
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.showConfirmAlert
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.store.comment.rating.StoreRatingDialog
import cao.cuong.supership.supership.ui.customer.store.info.StoreInfoFragment
import io.reactivex.Notification
import org.jetbrains.anko.AnkoContext

class StoreCommentFragment : BaseFragment() {

    companion object {
        internal fun getNewInstance(storeId: Long): StoreCommentFragment {
            val instance = StoreCommentFragment()
            instance.arguments = Bundle().apply {
                putLong(StoreInfoFragment.KEY_STORE_ID, storeId)
            }
            return instance
        }
    }

    internal lateinit var ui: StoreCommentFragmentUI
    internal lateinit var viewModel: StoreCommentFragmentViewModel
    private var storeId = -1L

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        storeId = arguments.getLong(StoreInfoFragment.KEY_STORE_ID)
        viewModel = StoreCommentFragmentViewModel(context)
        ui = StoreCommentFragmentUI(viewModel.storeComment.comments)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateProgressStatusObservable
                .observeOnUiThread()
                .subscribe(this::handleUpdateProgressDialogStatus)
        viewModel.getStoreCommentObservable
                .observeOnUiThread()
                .subscribe(this::handleGetStoreCommentCompleted)
        RxBus.listen(UpdateCommentUI::class.java)
                .observeOnUiThread()
                .subscribe(this::updateCommentUI)
        viewModel.getStoreComment(storeId)
    }

    override fun onBindViewModel() {

    }

    override fun handleUpdateProgressDialogStatus(status: Boolean) {
        ui.swipeRefreshLayout.isRefreshing = status
    }

    override fun loadMore(index: Int) {
        if (!ui.swipeRefreshLayout.isRefreshing) {
            viewModel.loadMore(storeId, index)
        }
    }

    internal fun onCommentButtonClicked() {
        hideKeyboard()
        if (viewModel.isLogin()) {
            val comment = ui.edtComment.text.toString().trim()
            ui.edtComment.text = null
            if (comment.isNotEmpty()) {
                viewModel.comment(storeId, comment)
            }
        } else {
            handleApiError(ApiException(401, "Bạn chưa đăng nhập. Vui lòng đăng nhập để tiếp tục."))
        }
    }

    internal fun onStarRateClicked() {
        hideKeyboard()
        val dialog = StoreRatingDialog(context, storeId, viewModel.storeComment.ratings)
        dialog.show()
    }

    internal fun onBackClicked() {
        hideKeyboard()
        if (ui.edtComment.text.toString().isNotEmpty()) {
            context.showConfirmAlert(R.string.leaveCommentConfirm) {
                activity.onBackPressed()
            }
        } else {
            activity.onBackPressed()
        }
    }

    internal fun onRefresh() {
        viewModel.currentPage = 1
        viewModel.getStoreComment(storeId)
    }

    private fun handleGetStoreCommentCompleted(notification: Notification<Boolean>) {
        if (notification.isOnNext) {
            ui.rlStarButton.visibility = View.VISIBLE
            if (viewModel.storeComment.starRated()) {
                ui.imgStarRate.setImageResource(R.drawable.ic_star_gold)
            } else {
                ui.imgStarRate.setImageResource(R.drawable.ic_star_black)
            }
            ui.commentAdapter.notifyDataSetChanged()
            return
        }
        if (notification.isOnError) {
            handleApiError(notification.error)
        }
    }

    private fun updateCommentUI(updateCommentUI: UpdateCommentUI) {
        if (viewModel.storeComment.starRated()) {
            ui.imgStarRate.setImageResource(R.drawable.ic_star_gold)
        } else {
            ui.imgStarRate.setImageResource(R.drawable.ic_star_black)
        }
    }
}
