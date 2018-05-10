package cao.cuong.supership.supership.ui.order.drink.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.OrderedDrink
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.order.OrderActivity
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
        val drink = (activity as? OrderActivity)?.getOrderedDrink(arguments.getInt(KEY_INDEX))
        if (drink != null) {
            orderedDrink = drink
        }
        ui = OrderedDrinkFragmentUI(orderedDrink.options)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(ui) {
            tvDrinkName.text = orderedDrink.name
            tvBillCount.text = getString(R.string.notOrderYet)
            updateDrinkPrice()
            val option = RequestOptions()
                    .placeholder(R.drawable.glide_place_holder)
            Glide.with(context)
                    .applyDefaultRequestOptions(option)
                    .asBitmap()
                    .load("https://vnshipperman.000webhostapp.com/uploads/${orderedDrink.image}")
                    .into(imgDrinkImage)
        }
        updateDrinkPrice()
    }

    override fun onBindViewModel() {

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

    }

    private fun updateDrinkPrice() {
        ui.tvPrice.text = context.getString(R.string.drinkPrice, orderedDrink.price)
        ui.tvDrinkCount.text = orderedDrink.count.toString()
        val totalOptionPrice = orderedDrink.price * orderedDrink.count
        ui.tvTotalPrice.text = context.getString(R.string.drinkTotalPrice, totalOptionPrice)
    }
}