package com.example.list_chars

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.extensions.observe
import com.example.list_chars.adapter.CharsListAdapter
import com.example.list_chars.adapter.AdapterState
import com.example.list_chars.databinding.FragmentFirstBinding
import com.example.list_chars.di.ListCharsModule.startModules
import com.example.list_chars.model.CharMapped
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListCharsFragment : Fragment() {

    private val viewAdapter by inject<CharsListAdapter>()

    private val viewModel by viewModel<CharsViewModel>()

    private var _binding: FragmentFirstBinding? = null

    private val RecyclerView.gridLayoutManager: GridLayoutManager?
        get() = layoutManager as? GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startModules()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        _binding?.lifecycleOwner = viewLifecycleOwner
        return _binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::onViewStateChange)
        observe(viewModel.data, ::onViewDataChange)
        onInitDataBinding()
    }


    private fun onInitDataBinding() {
        _binding?.viewModel = viewModel
        _binding?.includeList?.charactersList?.apply {
            adapter = viewAdapter
            gridLayoutManager?.spanSizeLookup = viewAdapter.getSpanSizeLookup()
        }
    }

    private fun onViewDataChange(viewData: PagedList<CharMapped>) {
        viewAdapter.submitList(viewData)
    }

    private fun onViewStateChange(viewState: CharsListViewState) {
        when (viewState) {
            is CharsListViewState.Load ->
                viewAdapter.submitState(AdapterState.AddChar)
            is CharsListViewState.LoadingFinish ->
                viewAdapter.submitState(AdapterState.AddLoad)
            is CharsListViewState.Error ->
                viewAdapter.submitState(AdapterState.AddError)
            else -> {
            }
        }
    }

}