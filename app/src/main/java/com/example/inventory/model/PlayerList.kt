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
                networkPlayer.first_name,
                networkPlayer.last_name,
                networkPlayer.pdga_number,
                networkPlayer.membership_status,
                networkPlayer.membership_expiration_date,
                networkPlayer.classification,
                networkPlayer.city,
                networkPlayer.state_prov,
                networkPlayer.country,
                networkPlayer.rating,
                networkPlayer.rating_effective_date,
                networkPlayer.official_status,
                networkPlayer.official_expiration_date,
                networkPlayer.last_modified
            )
        }
    }
}
