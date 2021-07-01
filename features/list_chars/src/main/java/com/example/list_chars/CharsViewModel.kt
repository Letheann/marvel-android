package com.example.list_chars

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import com.example.core.network.StatesRequest
import com.example.list_chars.paging.MAX_ELEMENTS
import com.example.list_chars.paging.PagingDataSourceFactory


class CharsViewModel(
    private val factoryPagingSource: PagingDataSourceFactory
) : ViewModel() {

    private val states = Transformations.switchMap(factoryPagingSource.sourceData) {
        it.states
    }

    val data = LivePagedListBuilder(factoryPagingSource, MAX_ELEMENTS).build()
    val state = Transformations.map(states) {
        when (it) {
            is StatesRequest.SuccessRequest -> {
                    CharsListViewState.Load
                }
            is StatesRequest.Loading ->
                if (it.isNew) {
                    CharsListViewState.LoadingFinish
                } else {
                    CharsListViewState.Loading
                }
            is StatesRequest.Error -> CharsListViewState.Error
            else -> CharsListViewState.Error
        }
    }


    fun retryAddCharactersList() {
        factoryPagingSource.retry()
    }

}
