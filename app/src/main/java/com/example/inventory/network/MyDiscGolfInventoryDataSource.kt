package com.example.inventory.network

import com.example.inventory.network.model.NetworkAuthentication
import com.example.inventory.network.retrofit.AuthenticationService
import com.example.inventory.network.retrofit.PdgaService
import com.google.gson.JsonObject

class MyDiscGolfInventoryDataSource(
    private val authenticationService: AuthenticationService,
    private val pdgaService: PdgaService
) : NetworkDataSource {

    override suspend fun sendAuthentication(
        userName: String,
        password: String
    ): Result<NetworkAuthentication> {
        val body = JsonObject()

        body.addProperty("username", userName)
        body.addProperty("password", password)

        return authenticationService.sendAuthentication(body)
    }

    override suspend fun logout(sessionId: String, tokenId: String, sessionName: String): Result<String> {
        return authenticationService.logout(
            JsonObject().apply {
                addProperty("X-CSRF-Token", tokenId)
                addProperty("Cookie", "$sessionName=$sessionId")
            }
        )
    }
}