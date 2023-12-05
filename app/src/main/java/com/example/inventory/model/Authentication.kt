package com.example.inventory.model

import com.example.inventory.network.model.NetworkAuthentication
import com.example.inventory.network.model.NetworkUser

data class Authentication(
    val sessionId: String,
    val sessionName: String,
    val token: String,
    val user: User?
) {
    companion object {
        fun fromNetwork(networkAuthentication: NetworkAuthentication): Authentication {
            return Authentication(
                networkAuthentication.sessid,
                networkAuthentication.session_name,
                networkAuthentication.token,
                networkAuthentication.user.let { User.fromNetwork(it) }
            )
        }
    }
}

data class User(
    val name: String,
    val mail: String
) {
    companion object {
        fun fromNetwork(networkUser: NetworkUser): User {
            return User(
                networkUser.name,
                networkUser.mail
            )
        }
    }
}