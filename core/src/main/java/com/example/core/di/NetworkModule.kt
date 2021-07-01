package com.example.core.di

import com.example.core.BuildConfig
import com.example.core.extensions.*
import com.example.core.network.MarvelService
import org.koin.dsl.module
import retrofit2.Retrofit


object CoreModule {
    fun getModules() = arrayListOf(networkModule)
    private val networkModule = module {
        single { Retrofit.Builder().build(BuildConfig.BASE_URL).provideInterface<MarvelService>() }
    }
}