package com.example.inventory.network.retrofit

import com.example.inventory.network.model.NetworkAuthentication
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {

    @POST(value = "/services/json/user/login")
    suspend fun sendAuthentication(
        @Body params: JsonObject
    ): Result<NetworkAuthentication>

    @POST(value = "/services/json/user/logout")
    suspend fun logout(
        @Body params: JsonObject
    ): Result<String>
}
