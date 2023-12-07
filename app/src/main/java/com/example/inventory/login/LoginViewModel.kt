package com.example.inventory.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.R
import com.example.inventory.common.AppPreferences
import com.example.inventory.common.Event
import com.example.inventory.common.SnackBarMessage
import com.example.inventory.data.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val _appPreferences: AppPreferences,
    private val _authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Event<Boolean?>>()
    private val _snackBar = MutableLiveData<Event<SnackBarMessage>>()
    private val _isLogInSuccessful = MutableLiveData<Event<Boolean?>>()

    val loading: LiveData<Event<Boolean?>> = _loading
    val snackBar: LiveData<Event<SnackBarMessage>> = _snackBar
    val isLogInSuccessful: LiveData<Event<Boolean?>> = _isLogInSuccessful

    fun sendAuthentication(userName: String, password: String) {
        viewModelScope.launch {
            _loading.value = Event(true)
            val result = _authenticationRepository.sendAuthentication(userName, password)
            if (result.isSuccess) {
                checkPreferences()
                result.getOrNull()?.let {
                    _appPreferences.sessionId = it.sessionId
                    _appPreferences.sessionName = it.sessionName
                    _appPreferences.sessionToken = it.token
                }
                val message = SnackBarMessage(R.string.message_log_in_success)
                _appPreferences.userName?.let { message.addFormattedMessage(it) }
                _snackBar.value = Event(message)
                _isLogInSuccessful.value = Event(true)
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

    private fun checkPreferences() {
        // Invalidate Previous User
        if (!_appPreferences.sessionId.isNullOrEmpty() &&
            !_appPreferences.sessionName.isNullOrEmpty() &&
            !_appPreferences.sessionToken.isNullOrEmpty()) {
            invalidatePreferences()
        }
    }

    fun invalidatePreferences() {
        _appPreferences.sessionId = null
        _appPreferences.sessionName = null
        _appPreferences.sessionToken = null
        _appPreferences.userName = null
    }

}