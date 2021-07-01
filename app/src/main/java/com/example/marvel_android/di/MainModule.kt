package com.example.marvel_android.di

import org.koin.dsl.module

object MainModule{
    fun getModules() = arrayListOf(mainModule)
    private val mainModule = module {}
}