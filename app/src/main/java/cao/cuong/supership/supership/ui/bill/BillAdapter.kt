package cao.cuong.supership.supership.ui.bill

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.ExpressBill
import cao.cuong.supership.supership.extension.getBillStatus
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk25.coroutines.onClick

class BillAdapter(private val expressBills: MutableList<ExpressBill>) : RecyclerView.Adapter<BillAdapter.BillItemHolder>() {

    internal val option = RequestOptions()
            .placeholder(R.drawable.glide_place_holder)
    internal var onBillExpressClicked: (Long) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillItemHolder {
        val ui = BillItemUI()
        return BillItemHolder(ui, ui.createView(AnkoContext.Companion.create(parent.context, parent, false)))
    }

    override fun getItemCount() = expressBills.size

    override fun onBindViewHolder(holder: BillItemHolder?, position: Int) {
        holder?.onBind()
    }

    inner class BillItemHolder(private val ui: BillItemUI, itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.onClick {
                onBillExpressClicked(expressBills[adapterPosition].id)
            }
        }

        internal fun onBind() {
            with(expressBills[adapterPosition]) {
                com.bumptech.glide.Glide.with(itemView.context)
                        .applyDefaultRequestOptions(option)
                        .asBitmap()
                        .load("https://vnshipperman.000webhostapp.com/uploads/$storeImage")
                        .into(ui.imgStoreIcon)
                ui.tvStoreName.text = storeName
                ui.tvBillStatus.text = itemView.context.getString(R.string.status, status.getBillStatus())
                ui.tvBillId.text = itemView.context.getString(R.string.billId, id)
                ui.tvBillPrice.text = itemView.context.getString(R.string.drinkPrice, price)
            }
        }
    }
}
