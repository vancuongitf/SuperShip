package cao.cuong.supership.supership.ui.store.info

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.Drink
import org.jetbrains.anko.AnkoContext

class DrinkAdapter(private val drink: MutableList<Drink>) : RecyclerView.Adapter<DrinkAdapter.DrinkHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkHolder {
        val ui = DrinkItemUI()
        return DrinkHolder(ui, ui.createView(AnkoContext.Companion.create(parent.context, parent, false)))
    }

    override fun getItemCount() = drink.size

    override fun onBindViewHolder(holder: DrinkHolder?, position: Int) {
    }

    inner class DrinkHolder(private val ui: DrinkItemUI, itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
