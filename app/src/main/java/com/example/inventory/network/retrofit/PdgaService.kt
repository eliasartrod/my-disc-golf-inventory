package com.example.inventory.network.retrofit

import com.example.inventory.network.model.NetworkPlayerList
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface PdgaService {

    @GET(value = "/services/json/players")
    suspend fun searchPlayers(
        @Header("Cookie") sessionCookie: String,
        @QueryMap options: Map<String, String>
    ): Result<NetworkPlayerList>
}