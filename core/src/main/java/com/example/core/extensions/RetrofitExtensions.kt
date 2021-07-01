package com.example.core.extensions

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun Retrofit.Builder.build(url: String): Retrofit {
    return this.baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

inline fun <reified R : Any> Retrofit.provideInterface(): R {
    return this.create(R::class.java)
}


