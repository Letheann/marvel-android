package com.example.list_chars.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.list_chars.CharsViewModel
import com.example.list_chars.adapter.holders.CharViewHolder
import com.example.list_chars.adapter.holders.ErrorViewHolder
import com.example.list_chars.adapter.holders.LoadingViewHolder
import com.example.list_chars.databinding.ListItemCharacterBinding
import com.example.list_chars.databinding.ListItemErrorBinding
import com.example.list_chars.databinding.ListItemLoadingBinding
import com.example.list_chars.model.CharMapped


internal enum class ItemView(val type: Int, val span: Int) {
    CHAR(type = 0, span = 1),
    LOADING(type = 1, span = 2),
    ERROR(type = 2, span = 2);

    companion object {
        fun valueOf(type: Int): ItemView = values().first { it.type == type }
    }
}

class CharsListAdapter(
    private val viewModel: CharsViewModel
) : PagedListAdapter<CharMapped, RecyclerView.ViewHolder>(object :
    DiffUtil.ItemCallback<CharMapped>() {
    override fun areItemsTheSame(old: CharMapped, new: CharMapped): Boolean =
        old.id == new.id

    override fun areContentsTheSame(old: CharMapped, new: CharMapped): Boolean =
        old == new
}) {

    private var state: AdapterState = AdapterState.AddChar
    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun submitList(pagedList: PagedList<CharMapped>?) {
        super.submitList(pagedList)
        if (pagedList.isNullOrEmpty()) {
            recyclerView?.scrollToPosition(0)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = null
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ItemView.valueOf(viewType)) {
            ItemView.CHAR -> CharViewHolder(ListItemCharacterBinding.inflate(inflater))
            ItemView.LOADING -> LoadingViewHolder(ListItemLoadingBinding.inflate(inflater))
            else -> ErrorViewHolder(ListItemErrorBinding.inflate(inflater))
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemView(position)) {
            ItemView.CHAR ->
                getItem(position)?.let {
                    if (holder is CharViewHolder) {
                        holder.bind(viewModel, it)
                    }
                }
            ItemView.ERROR ->
                if (holder is ErrorViewHolder) {
                    holder.bind(viewModel)
                }
            else -> {
            }
        }
    }

    override fun getItemId(position: Int) =
        when (getItemView(position)) {
            ItemView.CHAR -> getItem(position)?.id ?: -1L
            ItemView.LOADING -> 0L
            ItemView.ERROR -> 1L
        }

    override fun getItemCount() =
        if (state.RowExtra) {
            super.getItemCount() + 1
        } else {
            super.getItemCount()
        }

    override fun getItemViewType(position: Int) = getItemView(position).type

    fun submitState(newState: AdapterState) {
        val oldState = state
        state = newState
        if (newState.RowExtra && oldState != newState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    fun getSpanSizeLookup(): GridLayoutManager.SpanSizeLookup =
        object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return getItemView(position).span
            }
        }

    internal fun getItemView(position: Int) =
        if (state.RowExtra && position == itemCount - 1) {
            if (state.isAddError()) {
                ItemView.ERROR
            } else {
                ItemView.LOADING
            }
        } else {
            ItemView.CHAR
        }

}
