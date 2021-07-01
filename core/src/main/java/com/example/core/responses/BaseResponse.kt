package com.example.core.responses

data class BaseResponse<T>(
    val code: Any,
    val status: String,
    val message: String,
    val data: DataResponse<T>
)
