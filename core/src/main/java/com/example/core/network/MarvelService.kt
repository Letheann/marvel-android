package com.example.core.network

import com.example.core.responses.BaseResponse
import com.example.core.responses.CharResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {
    @GET("/v1/public/characters")
    suspend fun getChars(
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") timestamp: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): BaseResponse<CharResponse>
}
