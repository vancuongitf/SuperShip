package cao.cuong.supership.supership.ui.customer.store.comment

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.Comment
import cao.cuong.supership.supership.extension.getDateTimeFormat
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk25.coroutines.onLongClick

class CommentAdapter(private val comments: MutableList<Comment>) : RecyclerView.Adapter<CommentAdapter.CommentItemHolder>() {

    internal var onItemLongClick: (Comment) -> Unit = {}

    override fun getItemCount() = comments.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemHolder {
        val ui = CommentItemUI()
        return CommentItemHolder(ui, ui.createView(AnkoContext.Companion.create(parent.context, parent, false)))
    }

    override fun onBindViewHolder(holder: CommentItemHolder?, position: Int) {
        holder?.onBind()
    }


    inner class CommentItemHolder(private val ui: CommentItemUI, itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.onLongClick {
                if (adapterPosition > -1) {
                    onItemLongClick(comments[adapterPosition])
                }
            }
        }

        internal fun onBind() {
            with(comments[adapterPosition]) {
                ui.tvUserName.text = userName
                ui.tvComment.text = comment
                ui.tvCommentTime.text = time.getDateTimeFormat()
            }
        }
    }
}
