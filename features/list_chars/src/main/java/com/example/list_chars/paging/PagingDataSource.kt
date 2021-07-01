package com.example.list_chars.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.core.network.StatesRequest
import com.example.core.repositories.MarvelRepository
import com.example.list_chars.model.CharMapped
import com.example.list_chars.model.CharMapper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

const val INIT_ELEMENTS = 0
const val MAX_ELEMENTS = 50

open class PagingDataSource(
    private val scope: CoroutineScope,
    private val marvelRepository: MarvelRepository,
    private val mapper: CharMapper
) : PageKeyedDataSource<Int, CharMapped>() {

    val states = MutableLiveData<StatesRequest>()
    var retry: (() -> Unit)? = null


    @ExperimentalCoroutinesApi
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, CharMapped>
    ) {
        scope.launch {
            flow {
                val usersMapped = getChars(INIT_ELEMENTS)
                if (usersMapped.isNotEmpty()) {
                    emit(StatesRequest.SuccessRequest(isEmpty = false))
                } else {
                    emit(StatesRequest.Error())
                }
                callback.onResult(usersMapped, null, MAX_ELEMENTS)
            }.onStart {
                emit(StatesRequest.Loading())
            }.catch {
                retry = {
                    loadInitial(params, callback)
                }
                emit(StatesRequest.Error(true))
            }.flowOn(Dispatchers.Default).collect {
                states.postValue(it)
            }
        }
    }


    private suspend fun getChars(offset: Int) = marvelRepository.getChars(
        offset = offset,
        limit = MAX_ELEMENTS
    ).run {
        mapper.map(this)
    }

    @ExperimentalCoroutinesApi
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CharMapped>) {
        scope.launch {
            flow {
                val usersMapped = getChars(params.key)
                if (usersMapped.isNotEmpty()) {
                    emit(StatesRequest.SuccessRequest(isEmpty = false))
                } else {
                    emit(StatesRequest.Error())
                }
                callback.onResult(usersMapped, params.key + MAX_ELEMENTS)
            }.onStart {
                emit(StatesRequest.Loading(true))
            }.catch {
                retry = {
                    loadAfter(params, callback)
                }
                emit(StatesRequest.Error(true))
            }.flowOn(Dispatchers.Default).collect {
                states.postValue(it)
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, CharMapped>
    ) {
    }

    fun retryGet() {
        retry?.invoke()
    }

}