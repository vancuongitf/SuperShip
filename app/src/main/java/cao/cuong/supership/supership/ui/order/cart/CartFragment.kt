package cao.cuong.supership.supership.ui.order.cart

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.OrderedDrink
import cao.cuong.supership.supership.data.model.rxevent.UpdateOrderUI
import cao.cuong.supership.supership.data.model.ShipAddress
import cao.cuong.supership.supership.data.model.google.StoreAddress
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.data.source.remote.request.BillBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.*
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.location.LocationActivity
import cao.cuong.supership.supership.ui.order.OrderActivity
import org.jetbrains.anko.AnkoContext

class CartFragment:BaseFragment(){

    private lateinit var ui: CartFragmentUI
    private lateinit var viewModel: CartFragmentViewModel
    private var shipAddress: ShipAddress? = null
    private val orderedDrinks = mutableListOf<OrderedDrink>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = CartFragmentViewModel(context)
        ui = CartFragmentUI(orderedDrinks)
        ui.orderedDrinkAdapter.onDrinkCountChange = this::updateBillInfo
        return ui.createView(AnkoContext.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.progressDialogStatusObservable
                .observeOnUiThread()
                .subscribe(this::handleUpdateProgressDialogStatus)
        updateUI()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                LocationActivity.REQUEST_CODE_SEARCH_LOCATION -> {
                    data?.let {
                        val shipAddress = data.extras.getParcelable<ShipAddress>(LocationActivity.KEY_ADDRESS_RESULT)
                        shipAddress?.let {
                            this.shipAddress = it
                            updateUI()
                        }
                    }
                }
            }
        }
    }

    override fun onBindViewModel() {

    }

    internal fun onBackClicked() {
        activity.onBackPressed()
    }

    internal fun onCheckClicked() {
        (activity as? OrderActivity)?.let {
            val orderActivity = it
            val userName = ui.edtCustomerName.text.toString().trim()
            val phone = ui.edtPhone.text.toString()
            var message = ""
            if (shipAddress != null) {
                if (userName.isValidateFullName()) {
                    if (phone.isValidatePhoneNumber()) {
                        val billBody = BillBody(orderActivity.store.id, "", ui.edtCustomerName.text.toString(), ui.edtPhone.text.toString(), StoreAddress(shipAddress!!.address, shipAddress!!.latLng), shipAddress!!.distance.getShipFee().toInt(), shipAddress!!.shipRoad, orderActivity.orderedDrinks)
                        if (viewModel.isLogin()) {
                            viewModel.submitOrder(billBody)
                                    .subscribe(this::handleOrderSuccess, this::handleApiError)
                        } else {
                            context.showOkAlert(R.string.notification, R.string.requestLogin) {
                                (activity as? BaseActivity)?.startUserActivity()
                            }
                        }
                    } else {
                        message = "Vui lòng điền số điện thoại hợp lệ"
                    }
                } else {
                    message = "Vui lòng điền tên hợp lệ."
                }
            } else {
                message = "Vui lòng chọn địa chỉ giao hàng."
            }
            if (message.isNotEmpty()) {
                handleApiError(Throwable(message))
            }
        }
    }

    internal fun eventOrderDrinkListClicked() {

    }

    internal fun eventChooseAddressClicked() {
        (activity as? OrderActivity)?.store?.let {
            val intent = Intent(context, LocationActivity::class.java)
            intent.putExtras(Bundle().apply {
                putParcelable(LocationActivity.KEY_STORE_ADDRESS, StoreAddress(it.address, it.latLng))
            })
            startActivityForResult(intent, LocationActivity.REQUEST_CODE_SEARCH_LOCATION)
        }
    }

    private fun updateUI() {
        (activity as? OrderActivity)?.let {
            val activity = it
            orderedDrinks.clear()
            orderedDrinks.addAll(it.orderedDrinks)
            ui.tvStoreName.text = it.store.name
            ui.tvBillCost.text = context.getString(R.string.billPrice, it.getCartPrice())
            if (ui.edtCustomerName.text.isEmpty()) {
                ui.edtCustomerName.setText(viewModel.getUserInfo()?.fullName ?: "")
            }
            if (ui.edtPhone.text.isEmpty()) {
                ui.edtPhone.setText(viewModel.getUserInfo()?.phoneNumber ?: "")
            }
            shipAddress?.let {
                ui.tvAddress.text = it.address
                ui.tvBillShipCost.text = "${it.distance.getShipFee()} VND"
                ui.tvTotalCost.text = "${activity.getCartPrice() + it.distance.getShipFee()} VND"
            }
        }
    }

    private fun handleOrderSuccess(messageResponse: MessageResponse) {
        context.showOkAlert(R.string.notification, messageResponse.message) {
            RxBus.publish(UpdateOrderUI())
            activity.finish()
        }
    }

    private fun updateBillInfo() {
        (activity as? OrderActivity)?.let {
            val cartPrice = it.getCartPrice()
            ui.tvBillCost.text = context.getString(R.string.billPrice, cartPrice)
            shipAddress?.let {
                ui.tvTotalCost.text = "${cartPrice + it.distance.getShipFee()} VND"
            }
        }
    }
}
