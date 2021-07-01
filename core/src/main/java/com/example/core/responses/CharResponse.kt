package com.example.core.responses

data class CharResponse(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: CharThumbResponse
)
