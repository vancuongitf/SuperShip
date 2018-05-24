package cao.cuong.supership.supership.ui.customer.order.drink.item

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.BuildConfig
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.OrderedDrink
import cao.cuong.supership.supership.data.model.UpdateOrderedBills
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.bill.activity.BillActivity
import cao.cuong.supership.supership.ui.customer.order.OrderActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.AnkoContext

class OrderedDrinkFragment : BaseFragment() {

    companion object {

        private const val KEY_INDEX = "index"

        internal fun getNewInstance(index: Int): OrderedDrinkFragment {
            val instance = OrderedDrinkFragment()
            instance.arguments = Bundle().apply {
                putInt(KEY_INDEX, index)
            }
            return instance
        }
    }

    private lateinit var ui: OrderedDrinkFragmentUI
    private lateinit var orderedDrink: OrderedDrink

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        when (activity) {
            is OrderActivity -> {
                val drink = (activity as? OrderActivity)?.getOrderedDrink(arguments.getInt(KEY_INDEX))
                if (drink != null) {
                    orderedDrink = drink
                }
            }

            is BillActivity -> {
                val drink = (activity as? BillActivity)?.billInfo?.drinks?.get(arguments.getInt(KEY_INDEX))
                if (drink != null) {
                    Log.i("tag11","xxxsss")
                    orderedDrink = drink
                }
            }
            else -> {
                context.showOkAlert(Throwable("Xãy ra lỗi! Vui lòng thử lại sau.")) {
                    activity.onBackPressed()
                }
            }
        }
        ui = OrderedDrinkFragmentUI(orderedDrink.options)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? BillActivity)?.let {
            ui.llDrinkCount.visibility = View.GONE
        }
        with(ui) {
            tvDrinkName.text = orderedDrink.name
            tvBillCount.visibility = View.GONE
            ui.edtNote.setText(orderedDrink.note)
            updateDrinkPrice()
            val option = RequestOptions()
                    .placeholder(R.drawable.glide_place_holder)
            Glide.with(context)
                    .applyDefaultRequestOptions(option)
                    .asBitmap()
                    .load(BuildConfig.BASE_IMAGE_URL + orderedDrink.image)
                    .into(imgDrinkImage)
        }
        updateDrinkPrice()
    }

    override fun onBindViewModel() {

    }

    internal fun onBackClicked() {
        activity.onBackPressed()
        hideKeyboard()
    }

    internal fun onMinusDrinkClicked() {
        if (orderedDrink.count > 1) {
            orderedDrink.count--
            updateDrinkPrice()
        }else {
            context.showOkAlert(R.string.notification, R.string.minOrderCount)
        }
    }

    internal fun onAddDrinkClicked() {
        orderedDrink.count++
        updateDrinkPrice()
    }

    private fun updateDrinkPrice() {
        ui.tvPrice.text = context.getString(R.string.drinkPrice, orderedDrink.price)
        ui.tvDrinkCount.text = orderedDrink.count.toString()
        val totalOptionPrice = orderedDrink.price * orderedDrink.count
        ui.tvTotalPrice.text = context.getString(R.string.drinkTotalPrice, totalOptionPrice)
        RxBus.publish(UpdateOrderedBills())
    }
}
