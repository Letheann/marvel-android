package com.example.list_chars.di

import com.example.core.repositories.MarvelRepository
import com.example.list_chars.CharsViewModel
import com.example.list_chars.adapter.CharsListAdapter
import com.example.list_chars.model.CharMapper
import com.example.list_chars.paging.PagingDataSourceFactory
import com.example.list_chars.paging.PagingDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object ListCharsModule {

    private val loadKoinModules by lazy { loadKoinModules(getModules()) }

    fun startModules() = loadKoinModules

    private fun getModules() = arrayListOf(domainModule, viewModelModule, adapterModule)

    private val domainModule = module {
        factory { CoroutineScope(Dispatchers.Main.immediate + SupervisorJob()) }
        factory { CharMapper() }
        factory { MarvelRepository(get()) }
        factory { PagingDataSource(get(), get(), get()) }
        factory { PagingDataSourceFactory(get()) }
    }

    private val viewModelModule = module {
        viewModel { CharsViewModel(get()) }
    }

    private val adapterModule = module {
        factory { CharsListAdapter(get()) }
    }
}