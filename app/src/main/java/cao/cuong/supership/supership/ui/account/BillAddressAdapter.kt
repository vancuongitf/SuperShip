package cao.cuong.supership.supership.ui.account

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.BillAddress
import org.jetbrains.anko.AnkoContext

/**
 *
 * @author at-cuongcao.
 */
class BillAddressAdapter(private val billAddresses: MutableList<BillAddress>) : RecyclerView.Adapter<BillAddressAdapter.BillAddressViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillAddressViewHolder {
        val ui = BillAddressItemUI()
        return BillAddressViewHolder(ui, ui.createView(AnkoContext.Companion.create(parent.context, parent, false)))
    }

    override fun getItemCount() = 5

    override fun onBindViewHolder(holder: BillAddressViewHolder?, position: Int) {

    }

    inner class BillAddressViewHolder(private val ui: BillAddressItemUI, itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}