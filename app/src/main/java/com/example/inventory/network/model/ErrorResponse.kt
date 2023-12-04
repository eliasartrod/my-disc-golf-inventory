package com.example.inventory.network.model

data class ErrorResponse(
    val status: String,
    val data: Any?,
    val message: String,
    val errorCode: String?
)