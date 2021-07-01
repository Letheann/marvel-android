package com.example.list_chars.adapter.holders
import androidx.recyclerview.widget.RecyclerView
import com.example.list_chars.CharsViewModel
import com.example.list_chars.databinding.ListItemCharacterBinding
import com.example.list_chars.model.CharMapped

class CharViewHolder(
    private val binding: ListItemCharacterBinding
)  : RecyclerView.ViewHolder(binding.root) {

    fun bind(viewModel: CharsViewModel, item: CharMapped) {
        binding.viewModel = viewModel
        binding.character = item
        binding.executePendingBindings()
    }
}
