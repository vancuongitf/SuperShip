package cao.cuong.supership.supership.ui.customer.store.activity

import android.os.Bundle
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Drink
import cao.cuong.supership.supership.data.model.DrinkOption
import cao.cuong.supership.supership.extension.addFragment
import cao.cuong.supership.supership.extension.animRightToLeft
import cao.cuong.supership.supership.extension.replaceFragment
import cao.cuong.supership.supership.ui.customer.store.BaseStoreInfoActivity
import cao.cuong.supership.supership.ui.customer.store.comment.StoreCommentFragment
import cao.cuong.supership.supership.ui.customer.store.drink.create.CreateDrinkFragment
import cao.cuong.supership.supership.ui.customer.store.drink.info.DrinkFragment
import cao.cuong.supership.supership.ui.customer.store.edit.EditStoreInfoFragment
import cao.cuong.supership.supership.ui.customer.store.info.StoreInfoFragment
import cao.cuong.supership.supership.ui.customer.store.list.StoreListFragment
import cao.cuong.supership.supership.ui.customer.store.optional.add.AddDrinkOptionFragment
import cao.cuong.supership.supership.ui.customer.store.optional.list.OptionalFragment
import org.jetbrains.anko.setContentView

class StoreActivity : BaseStoreInfoActivity() {

    private lateinit var ui: StoreActivityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = StoreActivityUI()
        ui.setContentView(this)
        replaceFragment(R.id.storeActivityContainer, StoreListFragment())
        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.storeActivityContainer)
            if (shouldReload) {
                when (currentFragment) {
                    is StoreListFragment -> {
                        shouldReload = false
                        currentFragment.reloadData()
                    }
                    is StoreInfoFragment -> {
                        shouldReload = false
                        currentFragment.reloadData()
                    }
                }
            }
        }
    }

    override fun onBindViewModel() = Unit

    internal fun openStoreInfoFragment(storeId: Long) {
        addFragment(R.id.storeActivityContainer, StoreInfoFragment.getNewInstance(storeId), { it.animRightToLeft() }, StoreInfoFragment::class.java.simpleName)
    }

    internal fun openCreateDrinkFragment(storeId: Long) {
        addFragment(R.id.storeActivityContainer, CreateDrinkFragment.getNewInstance(storeId), { it.animRightToLeft() }, StoreInfoFragment::class.java.simpleName)
    }

    internal fun openDrinkOptionFragment(storeId: Long) {
        addFragment(R.id.storeActivityContainer, OptionalFragment.getNewInstance(storeId), { it.animRightToLeft() }, OptionalFragment::class.java.simpleName)
    }

    internal fun openAddDrinkOptionFragment(storeId: Long) {
        addFragment(R.id.storeActivityContainer, AddDrinkOptionFragment.getNewInstance(storeId), { it.animRightToLeft() }, AddDrinkOptionFragment::class.java.simpleName)
    }

    internal fun openDrinkFragment(drink: Drink) {
        addFragment(R.id.storeActivityContainer, DrinkFragment.getNewInstance(drink), { it.animRightToLeft() }, DrinkFragment::class.java.simpleName)
    }

    internal fun openEditDrinkFragment(drink: Drink) {
        addFragment(R.id.storeActivityContainer, CreateDrinkFragment.getNewInstance(-1L, drink), { it.animRightToLeft() }, "Edit-" + CreateDrinkFragment::class.java.simpleName)
    }

    internal fun openEditDrinkOptionFragment(drinkOption: DrinkOption) {
        addFragment(R.id.storeActivityContainer, AddDrinkOptionFragment.getNewInstance(-1, drinkOption), { it.animRightToLeft() }, "Edit-${AddDrinkOptionFragment::class.java.simpleName}")
    }

    internal fun openEditStoreInfoFragment() {
        addFragment(R.id.storeActivityContainer, EditStoreInfoFragment(), { it.animRightToLeft() }, EditStoreInfoFragment::class.java.simpleName)
    }

    internal fun openCommentFragment(storeId: Long) {
        addFragment(R.id.storeActivityContainer, StoreCommentFragment.getNewInstance(storeId), { it.animRightToLeft() }, StoreCommentFragment::class.java.simpleName)
    }
}
