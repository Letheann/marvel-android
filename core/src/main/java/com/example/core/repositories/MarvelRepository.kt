package com.example.core.repositories

import com.example.core.BuildConfig
import com.example.core.network.MarvelService
import com.example.core.extensions.toMD5
import com.example.core.responses.BaseResponse
import com.example.core.responses.CharResponse

private const val PUBLIC_KEY = BuildConfig.KEY_PUBLIC
private const val PRIVATE_KEY = BuildConfig.KEY_PRIVATE
private const val HASH_FORMAT = "%s%s%s"

class MarvelRepository(
    private val service: MarvelService
) {

    suspend fun getChars(offset: Int, limit: Int): BaseResponse<CharResponse> {
        val timestamp = System.currentTimeMillis().toString()
        return service.getChars(
            apiKey = PUBLIC_KEY,
            hash = generateApiHash(timestamp),
            timestamp = timestamp,
            offset = offset,
            limit = limit
        )
    }

    private fun generateApiHash(timestamp: String) =
        HASH_FORMAT.format(timestamp, PRIVATE_KEY, PUBLIC_KEY).toMD5()
}