package cao.cuong.supership.supership.ui.store.drink.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Drink
import cao.cuong.supership.supership.data.model.DrinkOption
import cao.cuong.supership.supership.data.model.OptionalBody
import cao.cuong.supership.supership.data.model.OrderedDrink
import cao.cuong.supership.supership.extension.getOrderedOptions
import cao.cuong.supership.supership.extension.hideKeyBoard
import cao.cuong.supership.supership.ui.base.BaseActivity
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
    private var orderCase = false
    private val drinkOptions = mutableListOf<DrinkOption>()
    private val orderedOption = mutableListOf<DrinkOption>()
    private val optionalBodies = mutableSetOf<OptionalBody>()
    private var drinkTotalPrice = 0
    private var count = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        try {
            drink = arguments.getSerializable(KEY_DRINK) as Drink
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
        (activity as? BaseActivity)?.hideKeyBoard()
        activity.onBackPressed()
    }

    internal fun onCheckClicked() {
        (activity as? BaseActivity)?.hideKeyBoard()
        (activity as? OrderActivity)?.let {
            if (count > 0) {
                orderedOption.clear()
                orderedOption.addAll(it.store.options.getOrderedOptions(optionalBodies))
                val price = drink.price + orderedOption.sumBy {
                    it.getTotalPrice()
                }
                it.drinkOrder(OrderedDrink(-1, drink.id, drink.name, price, drink.image, count, ui.edtNote.text.toString().trim(), orderedOption))
            }
            it.onBackPressed()
        }
    }

    internal fun onEditClicked() {
        // TODO: Handle later.
    }

    internal fun onAddDrinkClicked() {
        count++
        updateDrinkPrice()
    }

    internal fun onMinusDrinkClicked() {
        if (count > 0) {
            count--
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
        (activity as? BaseActivity)?.hideKeyBoard()
        ui.tvPrice.text = context.getString(R.string.drinkPrice, drink.price)
        ui.tvDrinkCount.text = count.toString()
        var totalOptionPrice = 0
        optionalBodies.clear()
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
                optionalBodies.add(OptionalBody(it.id, orderedItems))
            }
        }
        drinkTotalPrice = drink.price + totalOptionPrice
        ui.tvTotalPrice.text = context.getString(R.string.drinkTotalPrice, count * drinkTotalPrice)
    }

    private fun getDrinkOption() {
        (activity as? BaseStoreInfoActivity)?.getDrinkOptions(drink.options)?.let {
            drinkOptions.clear()
            drinkOptions.addAll(it)
        }
    }
}
