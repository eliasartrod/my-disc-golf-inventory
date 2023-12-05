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
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val _appPreferences: AppPreferences,
    private val _authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Event<Boolean?>>()
    private val _snackBar = MutableLiveData<Event<SnackBarMessage>>()

    val loading: LiveData<Event<Boolean?>> = _loading
    val snackBar: LiveData<Event<SnackBarMessage>> = _snackBar

    fun sendAuthentication(userName: String, password: String) {
        _loading.value = Event(true)
        viewModelScope.launch {
            val result = _authenticationRepository.sendAuthentication(userName, password)
            if (result.isSuccess) {
                result.getOrNull()?.let {
                    _appPreferences.sessionId = it.sessionId
                    _appPreferences.sessionName = it.sessionName
                    _appPreferences.sessionToken = it.token
                }
                val message = SnackBarMessage(R.string.message_log_in_success)
                _appPreferences.userName?.let { message.addFormattedMessage(it) }
                _snackBar.value = Event(message)
            } else {
                val errorBody =
                    (result.exceptionOrNull() as? HttpException)?.response()?.errorBody()
                        ?.string()
                val message = SnackBarMessage(R.string.message_error)
                if (errorBody != null) {
                    message.addFormattedMessage(errorBody)
                }
                _snackBar.value = Event(message)
            }
            _loading.value = Event(false)
        }
    }

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
                } else {
                    val errorBody =
                        (result.exceptionOrNull() as? HttpException)?.response()?.errorBody()
                            ?.string()
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

}