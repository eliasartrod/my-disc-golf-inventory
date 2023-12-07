package com.example.inventory.network.model

data class NetworkPlayerList (
    val sessid: String,
    val status: Long,
    val players: List<NetworkPlayer>
)

data class NetworkPlayer (
    val first_name: String,
    val last_name: String,
    val pdga_number: String,
    val membership_status: String,
    val membership_expiration_date: String,
    val classification: String,
    val city: String,
    val state_prov: String,
    val country: String,
    val rating: String,
    val rating_effective_date: String,
    val official_status: String,
    val official_expiration_date: String,
    val last_modified: String
)