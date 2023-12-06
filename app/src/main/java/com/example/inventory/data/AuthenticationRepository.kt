package com.example.inventory.data

import com.example.inventory.model.Authentication
import com.example.inventory.model.PlayerList
import com.example.inventory.network.NetworkDataSource
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
        return dataSource.logout(sessionId, tokenId, sessionName)
    }

    suspend fun searchPlayers(
        sessionId: String,
        sessionName: String
    ): Result<PlayerList?> {
        return dataSource.searchPlayers(sessionId, sessionName)
            .map { PlayerList.fromNetwork(it) }
    }

}