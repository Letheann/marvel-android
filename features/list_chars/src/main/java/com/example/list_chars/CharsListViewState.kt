
package com.example.list_chars

sealed class CharsListViewState {

    object Load : CharsListViewState()

    object Loading : CharsListViewState()

    object LoadingFinish : CharsListViewState()

    object Error : CharsListViewState()

    fun load() = this is Load

    fun loading() = this is Loading

    fun loadingFinish() = this is LoadingFinish

    fun error() = this is Error
}
