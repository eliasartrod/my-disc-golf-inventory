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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val _appPreferences: AppPreferences,
    private val _authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Event<Boolean>>()
    private val _snackBar = MutableLiveData<Event<SnackBarMessage>>()
    val loading: LiveData<Event<Boolean>> = _loading
    val snackBar: LiveData<Event<SnackBarMessage>> = _snackBar

    fun sendAuthentication(userName: String, password: String) {
        _loading.value = Event(true)
        viewModelScope.launch {
            val result = _authenticationRepository.sendAuthentication(userName, password)
            if (result.isSuccess) {
                val message = SnackBarMessage("Success!")
                _snackBar.value = Event(message)
            } else {
                val message = SnackBarMessage("Failed!")
                _snackBar.value = Event(message)
            }
            _loading.value = Event(false)
        }
    }

    fun logout() {
        val sessid = "session id here"
        val token = "token id here"
        val sessionName = "session name here"

        viewModelScope.launch {
            _loading.value = Event(true)
            val result = _authenticationRepository.logout(sessid, token, sessionName)
            if (result.isSuccess) {
                val message = SnackBarMessage("Success!")
                _snackBar.value = Event(message)
            } else {
                val errorBody = (result.exceptionOrNull() as? HttpException)?.response()?.errorBody()?.string()
                val message = SnackBarMessage(R.string.error)
                if (errorBody != null) {
                    message.addFormattedMessage(errorBody)
                }
                _snackBar.value = Event(message)
            }
            _loading.value = Event(false)
        }
    }

}