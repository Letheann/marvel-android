package com.example.marvel_android

import android.app.Application
import com.example.core.di.CoreModule
import com.example.marvel_android.di.MainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val modules = MainModule.getModules()
        modules.addAll(CoreModule.getModules())




        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(modules)
        }
    }
}