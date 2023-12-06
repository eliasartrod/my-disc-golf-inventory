package com.example.inventory.network.retrofit

import com.example.inventory.network.model.NetworkAuthentication
import com.example.inventory.network.model.NetworkPlayerList
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenticationService {

    @POST(value = "/services/json/user/login")
    suspend fun sendAuthentication(
        @Body params: JsonObject
    ): Result<NetworkAuthentication>

    @POST("/services/json/user/logout")
    suspend fun logout(
        @Header("X-CSRF-Token") tokenId: String,
        @Header("Cookie") sessionCookie: String
    ): Result<String>

    @GET(value = "/services/json/players")
    suspend fun searchPlayers(
        @Header("Cookie") sessionCookie: String
    ): Result<NetworkPlayerList>
}
