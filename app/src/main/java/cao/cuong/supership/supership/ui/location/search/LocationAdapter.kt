package cao.cuong.supership.supership.ui.location.search

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.google.AutoComplete
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk25.coroutines.onClick

class LocationAdapter(private val locations: MutableList<AutoComplete>) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    internal var onItemClicked: (autoComplete: AutoComplete) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val ui = LocationItemUI()
        return LocationViewHolder(ui, ui.createView(AnkoContext.Companion.create(parent.context, parent, false)))
    }

    override fun getItemCount() = locations.size

    override fun onBindViewHolder(holder: LocationViewHolder?, position: Int) {
        holder?.onBind(locations[position])
    }

    inner class LocationViewHolder(private val ui: LocationItemUI, itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.onClick {
                onItemClicked(locations[adapterPosition])
            }
        }

        fun onBind(autoComplete: AutoComplete) {
            ui.tvMainText.text = autoComplete.address.mainText
            ui.tvSecondary.text = autoComplete.address.secondaryText
        }
    }
}
