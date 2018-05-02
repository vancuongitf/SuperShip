package cao.cuong.supership.supership.ui.store.drink.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Drink
import cao.cuong.supership.supership.data.model.OrderDrink
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.order.OrderActivity
import cao.cuong.supership.supership.ui.store.activity.StoreActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.AnkoContext

class DrinkFragment : BaseFragment() {

    companion object {
        private const val KEY_DRINK = "drink"

        internal fun getNewInstance(drink: Drink): DrinkFragment {
            val instance = DrinkFragment()
            instance.arguments = Bundle().apply {
                this.putSerializable(KEY_DRINK, drink)
            }
            return instance
        }
    }

    internal lateinit var ui: DrinkFragmentUI
    internal lateinit var viewModel: DrinkFragmentViewModel
    internal var drink: Drink? = null
    private lateinit var orderInfo: OrderDrink
    private var orderCase = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        drink = arguments.getSerializable(KEY_DRINK) as? Drink
        drink?.let {
            orderInfo = OrderDrink(it.id, 0, mutableSetOf(), "")
        }
        viewModel = DrinkFragmentViewModel(context)
        checkOrderCase()
        ui = DrinkFragmentUI(orderCase)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drink?.let {
            with(ui) {
                tvDrinkTitle.text = it.name
                tvDrinkName.text = it.name
                // TODO: tvBillCount
                tvBillCount.text = getString(R.string.notOrderYet)
                updateDrinkPrice()
                val option = RequestOptions()
                        .placeholder(R.drawable.glide_place_holder)
                Glide.with(context)
                        .applyDefaultRequestOptions(option)
                        .asBitmap()
                        .load("https://vnshipperman.000webhostapp.com/uploads/${it.image}")
                        .into(imgDrinkImage)
            }

        }
    }

    override fun onBindViewModel() {

    }

    internal fun onBackClicked() {
        activity.onBackPressed()
    }

    internal fun onCheckClicked() {
        orderInfo.note = ui.edtNote.text.toString().trim()
        (activity as? OrderActivity)?.let {
            it.drinkOrder(orderInfo)
            it.onBackPressed()
        }
    }

    internal fun onAddDrinkClicked() {
        orderInfo.count++
        updateDrinkPrice()
    }

    internal fun onMinusDrinkClicked() {
        if (orderInfo.count > 0) {
            orderInfo.count--
            updateDrinkPrice()
        }
    }

    private fun checkOrderCase() {
        when (activity) {
            is OrderActivity -> orderCase = true

            is StoreActivity -> orderCase = false
        }
    }

    private fun updateDrinkPrice() {
        drink?.let {
            ui.tvPrice.text = context.getString(R.string.drinkPrice, it.price)
            ui.tvDrinkCount.text = orderInfo.count.toString()
            ui.tvTotalPrice.text = context.getString(R.string.drinkTotalPrice, orderInfo.count * it.price)
        }
    }
}
