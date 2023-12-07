package com.example.inventory.network

import com.example.inventory.network.model.NetworkAuthentication
import com.example.inventory.network.model.NetworkPlayerList
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

    override suspend fun logout(sessionId: String, tokenId: String, sessionName: String): Result<List<String>> {
        return authenticationService.logout(tokenId, "$sessionName=$sessionId"
        )
    }

    override suspend fun searchPlayers(
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
    ): Result<NetworkPlayerList> {

        val params = mutableMapOf<String, String>()

        // Add parameters to the map if they are not null or empty
        pdgaNumber?.let { params["pdga_number"] = it.toString() }
        lastName?.let { params["last_name"] = it }
        firstName?.let { params["first_name"] = it }
        playerClass?.let { params["class"] = it }
        city?.let { params["city"] = it }
        stateProv?.let { params["state_prov"] = it }
        country?.let { params["country"] = it }
        lastModified?.let { params["last_modified"] = it }
        limit?.let { params["limit"] = it.toString() }
        offset?.let { params["offset"] = it.toString() }

        return pdgaService.searchPlayers("$sessionName=$sessionId", params)
    }

}