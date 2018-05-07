package cao.cuong.supership.supership.ui.store.drink.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.*
import cao.cuong.supership.supership.extension.getOrderedOption
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.order.OrderActivity
import cao.cuong.supership.supership.ui.store.BaseStoreInfoActivity
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
    internal lateinit var drink: Drink
    private lateinit var orderInfo: OrderDrink
    private var orderCase = false
    private val drinkOptions = mutableListOf<DrinkOption>()
    private val orderedOption = mutableListOf<DrinkOption>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        try {
            drink = arguments.getSerializable(KEY_DRINK) as Drink
            orderInfo = OrderDrink(drink.id, 0, mutableSetOf(), "")
        } catch (e: ClassCastException) {
            activity.onBackPressed()
        }
        getDrinkOption()
        viewModel = DrinkFragmentViewModel(context)
        checkOrderCase()
        ui = DrinkFragmentUI(drinkOptions, orderCase)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            with(ui) {
                tvDrinkTitle.text = drink.name
                tvDrinkName.text = drink.name
                // TODO: tvBillCount
                tvBillCount.text = getString(R.string.notOrderYet)
                updateDrinkPrice()
                val option = RequestOptions()
                        .placeholder(R.drawable.glide_place_holder)
                Glide.with(context)
                        .applyDefaultRequestOptions(option)
                        .asBitmap()
                        .load("https://vnshipperman.000webhostapp.com/uploads/${drink.image}")
                        .into(imgDrinkImage)
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
            if (orderInfo.count > 0) {
                var price = drink.price
                orderedOption.forEach {
                    it.items.forEach {
                        price += it.price
                    }
                }
                it.drinkOrder(orderInfo)
            }
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

    internal fun updateDrinkPrice() {
        ui.tvPrice.text = context.getString(R.string.drinkPrice, drink.price)
        ui.tvDrinkCount.text = orderInfo.count.toString()
        var totalOptionPrice = 0
        orderInfo.optionals.clear()
        orderedOption.clear()
        drinkOptions.forEach {
            val orderedItems = mutableSetOf<Long>()
            it.items.forEach {
                if (it.isSelected) {
                    orderedItems.add(it.id)
                    totalOptionPrice += it.price
                }
            }
            if (orderedItems.isNotEmpty()) {
                orderInfo.optionals.add(OptionalBody(it.id, orderedItems))
            }
        }
        ui.tvTotalPrice.text = context.getString(R.string.drinkTotalPrice, orderInfo.count * (drink.price + totalOptionPrice))
    }

    private fun getDrinkOption() {
        (activity as? BaseStoreInfoActivity)?.getDrinkOptions(drink.options)?.let {
            drinkOptions.clear()
            drinkOptions.addAll(it)
        }
    }
}
