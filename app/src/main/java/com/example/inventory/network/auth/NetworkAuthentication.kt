package com.example.inventory.network.model

data class NetworkAuthentication(
    val sessid: String,
    val session_name: String,
    val token: String,
    val user: NetworkUser
)

data class NetworkUser(
    val name: String,
    val mail: String,
    val roles: Map<String, String>
)
