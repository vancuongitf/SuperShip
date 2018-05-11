package cao.cuong.supership.supership.ui.order

import android.os.Bundle
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Drink
import cao.cuong.supership.supership.data.model.OrderedDrink
import cao.cuong.supership.supership.data.model.rxevent.UpdateCartStatus
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.addFragment
import cao.cuong.supership.supership.extension.animRightToLeft
import cao.cuong.supership.supership.extension.replaceFragment
import cao.cuong.supership.supership.ui.order.cart.CartFragment
import cao.cuong.supership.supership.ui.store.BaseStoreInfoActivity
import cao.cuong.supership.supership.ui.store.drink.info.DrinkFragment
import cao.cuong.supership.supership.ui.store.info.StoreInfoFragment
import org.jetbrains.anko.setContentView

class OrderActivity : BaseStoreInfoActivity() {

    internal val orderedDrinks = mutableListOf<OrderedDrink>()

    private lateinit var ui: OrderActivityUI
    private lateinit var viewModel: OrderActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = OrderActivityUI()
        viewModel = OrderActivityViewModel(this)
        ui.setContentView(this)
        val storeId = intent.extras.getLong(StoreInfoFragment.KEY_STORE_ID)
        replaceFragment(R.id.orderActivityContainer, StoreInfoFragment.getNewInstance(storeId))
    }

    override fun onBindViewModel() = Unit

    internal fun openDrinkOrderFragment(drink: Drink) {
        addFragment(R.id.orderActivityContainer, DrinkFragment.getNewInstance(drink), { it.animRightToLeft() }, DrinkFragment::class.java.simpleName)
    }

    internal fun openCartFragment() {
        if (orderedDrinks.isNotEmpty()) {
            addFragment(R.id.orderActivityContainer, CartFragment(), { it.animRightToLeft() }, CartFragment::class.java.simpleName)
        }
    }

    internal fun openCartDrinkFragment() {

    }

    internal fun drinkOrder(orderedDrink: OrderedDrink) {
        val old = orderedDrinks.filter {
            it.sameWithOther(orderedDrink)
        }
        if (old.isNotEmpty()) {
            old.first().count += orderedDrink.count
        } else {
            orderedDrinks.add(orderedDrink)
        }
        RxBus.publish(UpdateCartStatus(orderedDrinks.isNotEmpty()))
    }

    internal fun getOrderedDrink(index: Int): OrderedDrink? {
        if (index in 0 until orderedDrinks.size) {
            return orderedDrinks[index]
        }
        return null
    }

    internal fun getCartPrice() = orderedDrinks.sumBy { it.price * it.count }
}
