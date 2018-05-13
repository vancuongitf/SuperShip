package cao.cuong.supership.supership.ui.customer.store.info

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Drink
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class DrinkAdapter(private val drinks: MutableList<Drink>) : RecyclerView.Adapter<DrinkAdapter.DrinkHolder>() {

    internal var onItemClicked: (drink: Drink) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkHolder {
        val ui = DrinkItemUI()
        return DrinkHolder(ui, ui.createView(AnkoContext.Companion.create(parent.context, parent, false)))
    }

    override fun getItemCount() = drinks.size

    override fun onBindViewHolder(holder: DrinkHolder?, position: Int) {
        holder?.onBind(drinks[position])
    }

    inner class DrinkHolder(private val ui: DrinkItemUI, itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val option = RequestOptions()
                .placeholder(R.drawable.glide_place_holder)

        init {
            itemView.onClick {
                onItemClicked(drinks[adapterPosition])
            }
        }

        internal fun onBind(drink: Drink) {
            Glide.with(itemView.context)
                    .applyDefaultRequestOptions(option)
                    .asBitmap()
                    .load("https://vnshipperman.000webhostapp.com/uploads/${drink.image}")
                    .into(ui.imgDrinkImage)

            ui.tvDrinkName.text = drink.name
            ui.tvDrinkPrice.text = itemView.context.getString(R.string.drinkPrice, drink.price)
            if (drink.orderCount > 0) {
                ui.tvDrinkBillCount.textColorResource = R.color.colorBlue
                ui.tvDrinkBillCount.text = itemView.context.getString(R.string.orderCount, drink.orderCount)
            } else {
                ui.tvDrinkBillCount.textColorResource = R.color.colorGray
                ui.tvDrinkBillCount.text = itemView.context.getString(R.string.notOrderYet)
            }
        }
    }
}
