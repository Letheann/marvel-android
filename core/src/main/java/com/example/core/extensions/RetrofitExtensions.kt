package com.example.core.extensions

import com.example.core.network.CurlLoggerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val client = OkHttpClient.Builder().addInterceptor(CurlLoggerInterceptor())
fun Retrofit.Builder.build(url: String): Retrofit {
    return this.baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client.build())
        .build()
}

inline fun <reified R : Any> Retrofit.provideInterface(): R {
    return this.create(R::class.java)
}


