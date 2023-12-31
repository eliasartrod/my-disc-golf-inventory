package com.example.inventory.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.R
import com.example.inventory.common.AppPreferences
import com.example.inventory.common.Event
import com.example.inventory.common.SnackBarMessage
import com.example.inventory.data.AuthenticationRepository
import com.example.inventory.model.Player
import com.example.inventory.model.PlayerList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val _appPreferences: AppPreferences,
    private val _authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Event<Boolean?>>()
    private val _snackBar = MutableLiveData<Event<SnackBarMessage>>()
    private val _isLogOutSuccessful = MutableLiveData<Event<Boolean?>>()
    private val _playerList = MutableLiveData<PlayerList>()

    val loading: LiveData<Event<Boolean?>> = _loading
    val snackBar: LiveData<Event<SnackBarMessage>> = _snackBar
    val isLogoutSuccessful: LiveData<Event<Boolean?>> = _isLogOutSuccessful
    val playerList: LiveData<PlayerList> = _playerList

    fun logout() {
        val sessionId = _appPreferences.sessionId
        val sessionToken = _appPreferences.sessionToken
        val sessionName = _appPreferences.sessionName

        viewModelScope.launch {
            _loading.value = Event(true)
            if (sessionId != null && sessionToken != null && sessionName != null) {
                val result = _authenticationRepository.logout(sessionId, sessionToken, sessionName)
                if (result.isSuccess) {
                    val message = SnackBarMessage(R.string.message_log_out_success)
                    _snackBar.value = Event(message)
                    _isLogOutSuccessful.value = Event(true)
                    invalidatePreferences()
                } else {
                    val errorBody = result.exceptionOrNull()?.localizedMessage
                    val message = SnackBarMessage(R.string.message_error)
                    if (errorBody != null) {
                        message.addFormattedMessage(errorBody)
                    }
                    _snackBar.value = Event(message)
                }
                _loading.value = Event(false)
            }
        }
    }

    fun searchPlayers(
        pdgaNumber: Int?,
        lastName: String?,
        firstName: String?,
        playerClass: String?,
        city: String?,
        stateProv: String?,
        country: String?,
        lastModified: String?,
        limit: Int?,
        offset: Int?
    ) {
        val sessionId = _appPreferences.sessionId
        val sessionName = _appPreferences.sessionName

        if (sessionId != null && sessionName != null) {
            viewModelScope.launch {
                _loading.value = Event(true)
                val result = _authenticationRepository.searchPlayers(
                    sessionId,
                    sessionName,
                    pdgaNumber,
                    lastName,
                    firstName,
                    playerClass,
                    city,
                    stateProv,
                    country,
                    lastModified,
                    limit,
                    offset
                )
                if (result.isSuccess) {
                    result.getOrNull().let {
                        _playerList.value = it
                    }
                } else {
                    val errorBody = result.exceptionOrNull()?.localizedMessage
                    val message = SnackBarMessage(R.string.message_error)
                    if (errorBody != null) {
                        message.addFormattedMessage(errorBody)
                    }
                    _snackBar.value = Event(message)
                }
                _loading.value = Event(false)
            }
        }
    }

    fun getSessionId(): String? {
        return _appPreferences.sessionId
    }

    private fun invalidatePreferences() {
        _appPreferences.sessionId = null
        _appPreferences.sessionName = null
        _appPreferences.sessionToken = null
        _appPreferences.userName = null
    }
}