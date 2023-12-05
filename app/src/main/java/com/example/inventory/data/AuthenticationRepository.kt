package com.example.inventory.data

import com.example.inventory.model.Authentication
import com.example.inventory.network.NetworkDataSource
import retrofit2.HttpException
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val dataSource: NetworkDataSource
) {

    suspend fun sendAuthentication(
        userName: String,
        password: String
    ): Result<Authentication?> {
        return dataSource.sendAuthentication(userName, password)
            .map { Authentication.fromNetwork(it) }
    }

    suspend fun logout(
        sessionId: String,
        tokenId: String,
        sessionName: String
    ): Result<String> {
        return try {
            val result = dataSource.logout(sessionId, tokenId, sessionName)
            if (result.isSuccess) {
                val responseBody = result.getOrNull() ?: ""
                Result.success(responseBody)
            } else {
                val errorBody = result.exceptionOrNull()?.let { exception ->
                    if (exception is HttpException) {
                        exception.response()?.errorBody()?.string()
                    } else {
                        null
                    }
                }
                Result.failure(Exception("Unsuccessful response: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}