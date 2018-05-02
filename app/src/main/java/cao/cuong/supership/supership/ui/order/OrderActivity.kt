package cao.cuong.supership.supership.ui.order

import android.os.Bundle
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Drink
import cao.cuong.supership.supership.data.model.OrderDrink
import cao.cuong.supership.supership.data.model.RxEvent.UpdateCartStatus
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.addFragment
import cao.cuong.supership.supership.extension.animRightToLeft
import cao.cuong.supership.supership.extension.replaceFragment
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.store.drink.info.DrinkFragment
import cao.cuong.supership.supership.ui.store.info.StoreInfoFragment
import org.jetbrains.anko.setContentView

class OrderActivity : BaseActivity() {

    private lateinit var ui: OrderActivityUI
    private val cart = mutableListOf<OrderDrink>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = OrderActivityUI()
        ui.setContentView(this)
        val storeId = intent.extras.getLong(StoreInfoFragment.KEY_STORE_ID)
        replaceFragment(R.id.orderActivityContainer, StoreInfoFragment.getNewInstance(storeId))
    }

    override fun onBindViewModel() {

    }

    internal fun openDrinkOrderFragment(drink: Drink) {
        addFragment(R.id.orderActivityContainer, DrinkFragment.getNewInstance(drink), { it.animRightToLeft() }, DrinkFragment::class.java.simpleName)
    }

    internal fun drinkOrder(orderDrink: OrderDrink) {
        val old = cart.filter {
            it.isSameWithOther(orderDrink)
        }
        if (old.isNotEmpty()) {
            old.first().count += orderDrink.count
        } else {
            cart.add(orderDrink)
        }
        RxBus.publish(UpdateCartStatus(cart.isNotEmpty()))
    }
}
