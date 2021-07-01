package com.example.core.mapper

interface Mapper<T, U> {
    suspend fun map(from: T): U
}