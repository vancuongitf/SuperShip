package cao.cuong.supership.supership.ui.store.optional.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.DrinkOption
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk25.coroutines.onClick

class OptionalAdapter(private val options: MutableList<DrinkOption>, private val adapterType: OptionalAdapter.AdapterType) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_VIEW_TYPE_MULTI = 1
    }

    internal var onItemAction: (view: View, drinkOption: DrinkOption) -> Unit = { _, _ -> }
    internal var onOptionSelectedChange: () -> Unit = {}

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
            ui.onCheckedChange = { index, isChecked ->
                options[adapterPosition].items[index].isSelected = isChecked
                onOptionSelectedChange()
            }

            when (adapterType) {
                AdapterType.ORDER -> {
                    ui.imgEdit.visibility = View.GONE
                    ui.imgDelete.visibility = View.GONE
                    ui.imgApply.visibility = View.GONE
                    ui.imgClear.visibility = View.GONE
                }

                AdapterType.CREATE_OPTION -> {
                    ui.imgEdit.visibility = View.VISIBLE
                    ui.imgDelete.visibility = View.VISIBLE
                    ui.imgApply.visibility = View.GONE
                    ui.imgClear.visibility = View.GONE

                    ui.imgEdit.onClick {
                        onItemAction(ui.imgEdit, options[adapterPosition])
                    }

                    ui.imgDelete.onClick {
                        onItemAction(ui.imgDelete, options[adapterPosition])
                    }
                }

                AdapterType.CREATE_DRINK -> {
                    ui.imgEdit.visibility = View.GONE
                    ui.imgDelete.visibility = View.GONE
                    ui.imgApply.visibility = View.VISIBLE
                    ui.imgClear.visibility = View.VISIBLE

                    ui.imgApply.onClick {
                        onItemAction(ui.imgApply, options[adapterPosition])
                    }

                    ui.imgClear.onClick {
                        onItemAction(ui.imgClear, options[adapterPosition])
                    }
                }

                AdapterType.BILL -> {
                    ui.imgEdit.visibility = View.GONE
                    ui.imgDelete.visibility = View.GONE
                    ui.imgApply.visibility = View.GONE
                    ui.imgClear.visibility = View.GONE
                    ui.checkBoxes.forEach {
                        it.checkBox.isEnabled = false
                    }
                }
            }
        }

        internal fun onBind() {
            when (adapterType) {
                AdapterType.CREATE_DRINK -> {
                    if (options[adapterPosition].isSelected) {
                        ui.imgClear.visibility = View.VISIBLE
                        ui.imgApply.visibility = View.GONE
                    } else {
                        ui.imgClear.visibility = View.GONE
                        ui.imgApply.visibility = View.VISIBLE
                    }
                }
            }
            ui.tvOptionName.text = options[adapterPosition].name
            ui.checkBoxes.withIndex().forEach {
                if (it.index < options[adapterPosition].items.size) {
                    it.value.visibility = View.VISIBLE
                    val index = it.index
                    it.value.checkBox.isChecked = options[adapterPosition].items[index].isSelected
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
            ui.onCheckedChange = { index, isChecked ->
                options[adapterPosition].items[index].isSelected = isChecked
                onOptionSelectedChange()
                if (isChecked) {
                    ui.radios.withIndex().forEach {
                        if (it.index != index) {
                            it.value.radio.isChecked = false
                        }
                    }
                }
            }

            when (adapterType) {
                AdapterType.ORDER, AdapterType.BILL -> {
                    ui.imgEdit.visibility = View.GONE
                    ui.imgDelete.visibility = View.GONE
                    ui.imgApply.visibility = View.GONE
                    ui.imgClear.visibility = View.GONE
                }

                AdapterType.CREATE_OPTION -> {
                    ui.imgEdit.visibility = View.VISIBLE
                    ui.imgDelete.visibility = View.VISIBLE
                    ui.imgApply.visibility = View.GONE
                    ui.imgClear.visibility = View.GONE

                    ui.imgEdit.onClick {
                        onItemAction(ui.imgEdit, options[adapterPosition])
                    }

                    ui.imgDelete.onClick {
                        onItemAction(ui.imgDelete, options[adapterPosition])
                    }
                }

                AdapterType.CREATE_DRINK -> {
                    ui.imgEdit.visibility = View.GONE
                    ui.imgDelete.visibility = View.GONE
                    ui.imgApply.visibility = View.VISIBLE
                    ui.imgClear.visibility = View.VISIBLE

                    ui.imgApply.onClick {
                        onItemAction(ui.imgApply, options[adapterPosition])
                    }

                    ui.imgClear.onClick {
                        onItemAction(ui.imgClear, options[adapterPosition])
                    }
                }
            }
        }

        internal fun onBind() {
            when (adapterType) {
                AdapterType.CREATE_DRINK -> {
                    if (options[adapterPosition].isSelected) {
                        ui.imgClear.visibility = View.VISIBLE
                        ui.imgApply.visibility = View.GONE
                    } else {
                        ui.imgClear.visibility = View.GONE
                        ui.imgApply.visibility = View.VISIBLE
                    }
                }
            }
            ui.tvOptionName.text = options[adapterPosition].name
            ui.radios.withIndex().forEach {
                if (it.index < options[adapterPosition].items.size) {
                    it.value.visibility = View.VISIBLE
                    val index = it.index
                    it.value.radio.isChecked = options[adapterPosition].items[index].isSelected
                    it.value.radio.text = options[adapterPosition].items[index].name
                    it.value.tvPrice.text = itemView.context.getString(R.string.drinkPrice, options[adapterPosition].items[index].price)
                } else {
                    it.value.visibility = View.GONE
                }
            }
        }
    }

    enum class AdapterType {
        ORDER,
        CREATE_OPTION,
        CREATE_DRINK,
        BILL
    }
}
