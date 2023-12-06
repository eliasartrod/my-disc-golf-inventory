package com.example.inventory.network.retrofit

import com.example.inventory.network.model.NetworkPlayerList
import retrofit2.http.GET
import retrofit2.http.Header

interface PdgaService {

    @GET(value = "/services/json/players")
    suspend fun searchPlayers(
        @Header("Cookie") sessionCookie: String
    ): Result<NetworkPlayerList>
}