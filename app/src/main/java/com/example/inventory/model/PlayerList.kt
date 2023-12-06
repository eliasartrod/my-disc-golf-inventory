package com.example.inventory.model

import com.example.inventory.network.model.NetworkPlayer
import com.example.inventory.network.model.NetworkPlayerList

data class PlayerList(
    val sessid: String,
    val status: Long,
    val players: List<Player>
) {
    companion object {
        fun fromNetwork(networkPlayerList: NetworkPlayerList): PlayerList {
            return PlayerList(
                networkPlayerList.sessid,
                networkPlayerList.status,
                networkPlayerList.players.map { Player.fromNetwork(it) }
            )
        }
    }
}

data class Player(
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
) {
    companion object {
        fun fromNetwork(networkPlayer: NetworkPlayer): Player {
            return Player(
                networkPlayer.firstName,
                networkPlayer.lastName,
                networkPlayer.pdgaNumber,
                networkPlayer.membershipStatus,
                networkPlayer.membershipExpirationDate,
                networkPlayer.classification,
                networkPlayer.city,
                networkPlayer.stateProv,
                networkPlayer.country,
                networkPlayer.rating,
                networkPlayer.ratingEffectiveDate,
                networkPlayer.officialStatus,
                networkPlayer.officialExpirationDate,
                networkPlayer.lastModified
            )
        }
    }
}
