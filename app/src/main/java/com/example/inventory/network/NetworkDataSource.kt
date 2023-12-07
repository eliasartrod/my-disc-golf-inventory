package com.example.inventory.network

import com.example.inventory.network.model.NetworkAuthentication
import com.example.inventory.network.model.NetworkPlayerList

/**
 * Definition of network access data methods
 */
interface NetworkDataSource {

    suspend fun sendAuthentication(
        userName: String,
        password: String
    ): Result<NetworkAuthentication>

    suspend fun logout(
        sessionId: String,
        tokenId: String,
        sessionName: String
    ): Result<List<String>>

    suspend fun searchPlayers(
        sessionId: String,
        sessionName: String,
        pdgaNumber: Int?,
        lastName: String?,
        firstName: String?,
        playerClass: String?,
        city: String?,
        stateProv: String?,
        country: String?,
        lastModified: String?,
        limit: Int?,
        offset: Int?
    ): Result<NetworkPlayerList>
}