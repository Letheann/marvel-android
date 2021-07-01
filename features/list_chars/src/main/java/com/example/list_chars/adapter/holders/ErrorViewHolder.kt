
package com.example.list_chars.adapter.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.list_chars.CharsViewModel
import com.example.list_chars.databinding.ListItemErrorBinding

class ErrorViewHolder(
   private val binding: ListItemErrorBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(viewModel: CharsViewModel) {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}
