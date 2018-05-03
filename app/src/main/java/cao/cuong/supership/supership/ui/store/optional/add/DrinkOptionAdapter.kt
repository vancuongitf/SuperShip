package cao.cuong.supership.supership.ui.store.optional.add

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.source.remote.request.DrinkOptionItemBody
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk25.coroutines.onClick

class DrinkOptionAdapter(private val items: MutableList<DrinkOptionItemBody>) : RecyclerView.Adapter<DrinkOptionAdapter.DrinkOptionItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkOptionItemHolder {
        val ui = DrinkOptionItemUI()
        return DrinkOptionItemHolder(ui, ui.createView(AnkoContext.Companion.create(parent.context, parent, false)))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DrinkOptionItemHolder?, position: Int) {
        holder?.onBind()
    }

    inner class DrinkOptionItemHolder(private val ui: DrinkOptionItemUI, itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            ui.imgDelete.onClick {

            }
        }

        fun onBind() {
            with(items[adapterPosition]) {
                ui.tvItemName.text = name
                ui.tvItemPrice.text = itemView.context.getString(R.string.drinkPrice, price)
            }
        }
    }
}