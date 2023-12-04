package com.example.inventory.network

import com.example.inventory.network.model.NetworkAuthentication

/**
 * Definition of network access data methods
 */
interface NetworkDataSource {

    suspend fun sendAuthentication(
        userName: String,
        password: String
    ): Result<NetworkAuthentication?>

    suspend fun logout(
        sessionId: String,
        tokenId: String,
        sessionName: String
    ): Result<String>
}