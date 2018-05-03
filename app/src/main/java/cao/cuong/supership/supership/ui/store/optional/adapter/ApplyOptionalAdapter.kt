package cao.cuong.supership.supership.ui.store.optional.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.DrinkOption
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk25.coroutines.onClick

class ApplyOptionalAdapters(private val options: MutableList<DrinkOption>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_VIEW_TYPE_MULTI = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        ITEM_VIEW_TYPE_MULTI -> {
            val ui = MultiChooseItemUI()
            MultiChooseHolder(ui, ui.createView(AnkoContext.Companion.create(parent.context, parent, false)))
        }

        else -> {
            val ui = SingleChooseItemUI()
            SingleChooseHolder(ui, ui.createView(AnkoContext.Companion.create(parent.context, parent, false)))
        }
    }

    override fun getItemCount() = options.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (getItemViewType(position)) {
            ITEM_VIEW_TYPE_MULTI -> {
                (holder as? MultiChooseHolder)?.onBind()
            }

            else -> {
                (holder as? SingleChooseHolder)?.onBind()
            }
        }
    }

    override fun getItemViewType(position: Int) = options[position].multiChoose

    inner class MultiChooseHolder(private val ui: MultiChooseItemUI, itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            ui.imgDelete.visibility = View.GONE
            ui.imgEdit.visibility = View.GONE
            ui.imgApply.onClick {
                options[adapterPosition].isSelected = true
                notifyDataSetChanged()
            }
            ui.imgClear.onClick {
                options[adapterPosition].isSelected = false
                notifyDataSetChanged()
            }
        }

        internal fun onBind() {
            ui.tvOptionName.text = options[adapterPosition].name
            if (options[adapterPosition].isSelected) {
                ui.imgClear.visibility = View.VISIBLE
                ui.imgApply.visibility = View.GONE
            } else {
                ui.imgClear.visibility = View.GONE
                ui.imgApply.visibility = View.VISIBLE
            }
            ui.checkBoxes.withIndex().forEach {
                if (it.index < options[adapterPosition].items.size) {
                    it.value.visibility = View.VISIBLE
                    val index = it.index
                    it.value.checkBox.text = options[adapterPosition].items[index].name
                    it.value.tvPrice.text = itemView.context.getString(R.string.drinkPrice, options[adapterPosition].items[index].price)
                } else {
                    it.value.visibility = View.GONE
                }
            }
        }
    }

    inner class SingleChooseHolder(private val ui: SingleChooseItemUI, itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            ui.imgDelete.visibility = View.GONE
            ui.imgEdit.visibility = View.GONE
            ui.imgApply.onClick {
                options[adapterPosition].isSelected = true
                notifyDataSetChanged()
            }
            ui.imgClear.onClick {
                options[adapterPosition].isSelected = false
                notifyDataSetChanged()
            }
            ui.onCheckedChange = { index, isChecked ->
                if (isChecked) {
                    ui.radios.withIndex().forEach {
                        if (it.index != index) {
                            it.value.radio.isChecked = false
                        }
                    }
                }
            }
        }

        internal fun onBind() {
            ui.tvOptionName.text = options[adapterPosition].name
            if (options[adapterPosition].isSelected) {
                ui.imgClear.visibility = View.VISIBLE
                ui.imgApply.visibility = View.GONE
            } else {
                ui.imgClear.visibility = View.GONE
                ui.imgApply.visibility = View.VISIBLE
            }
            ui.radios.withIndex().forEach {
                if (it.index < options[adapterPosition].items.size) {
                    it.value.visibility = View.VISIBLE
                    val index = it.index
                    it.value.radio.text = options[adapterPosition].items[index].name
                    it.value.tvPrice.text = itemView.context.getString(R.string.drinkPrice, options[adapterPosition].items[index].price)
                } else {
                    it.value.visibility = View.GONE
                }
            }
        }
    }
}
