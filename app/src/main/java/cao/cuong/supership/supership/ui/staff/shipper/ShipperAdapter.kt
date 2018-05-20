package cao.cuong.supership.supership.ui.staff.shipper

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.ShipperExpress
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk25.coroutines.onClick

class ShipperAdapter(private val shippers: MutableList<ShipperExpress>) : RecyclerView.Adapter<ShipperAdapter.ShipperItemHolder>() {

    internal var onItemClicked: (Long) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipperItemHolder {
        val ui = ShipperItemUI()
        return ShipperItemHolder(ui, ui.createView(AnkoContext.Companion.create(parent.context, parent, false)))
    }

    override fun getItemCount() = shippers.size

    override fun onBindViewHolder(holder: ShipperItemHolder?, position: Int) {
        holder?.onBind()
    }


    inner class ShipperItemHolder(private val ui: ShipperItemUI, itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.onClick {
                onItemClicked(shippers[adapterPosition].id)
            }
        }

        fun onBind() {
            ui.tvName.text = "Tên: ${shippers[adapterPosition].fullName}"
            ui.tvAddress.text = "Địa chỉ: ${shippers[adapterPosition].address}"
            ui.tvPhone.text = "Số điện thoại: ${shippers[adapterPosition].phoneNumber}"
            ui.tvId.text = "Mã shipper: ${shippers[adapterPosition].id}"
        }
    }
}