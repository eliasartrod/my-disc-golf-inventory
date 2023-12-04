package com.example.inventory.model

import com.example.inventory.network.model.NetworkAuthentication
import com.example.inventory.network.model.NetworkUser

data class Authentication(
    val sessionId: String,
    val sessionName: String,
    val token: String,
    val user: User
) {
    companion object {
        fun fromNetwork(networkAuthentication: NetworkAuthentication): Authentication {
            return Authentication(
                networkAuthentication.sessid,
                networkAuthentication.session_name,
                networkAuthentication.token,
                User.fromNetwork(networkAuthentication.user)
            )
        }
    }
}

data class User(
    val name: String,
    val mail: String,
    val login: String,
    val status: String
) {
    companion object {
        fun fromNetwork(networkUser: NetworkUser): User {
            return User(
                networkUser.name,
                networkUser.mail,
                networkUser.login,
                networkUser.status
            )
        }
    }
}