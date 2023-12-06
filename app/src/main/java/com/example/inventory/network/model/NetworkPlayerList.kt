package com.example.inventory.network.model

data class NetworkPlayerList (
    val sessid: String,
    val status: Long,
    val players: List<NetworkPlayer>
)

data class NetworkPlayer (
    val firstName: String,
    val lastName: String,
    val pdgaNumber: String,
    val membershipStatus: String,
    val membershipExpirationDate: String,
    val classification: String,
    val city: String,
    val stateProv: String,
    val country: String,
    val rating: String,
    val ratingEffectiveDate: String,
    val officialStatus: String,
    val officialExpirationDate: String,
    val lastModified: String
)