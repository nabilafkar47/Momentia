package com.dicoding.picodiploma.momentia.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.momentia.data.StoryRepository
import com.dicoding.picodiploma.momentia.data.pref.UserModel
import com.dicoding.picodiploma.momentia.data.remote.model.LoginResponse
import com.dicoding.picodiploma.momentia.utils.Event
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: StoryRepository) : ViewModel() {

    val alertMessage: LiveData<Event<String>> = repository.alertMessage
    val isLoading: LiveData<Boolean> = repository.isLoading
    val loginResponse: LiveData<LoginResponse> = repository.loginResponse
    private val _isSessionSaved = MutableLiveData<Boolean>()
    val isSessionSaved: LiveData<Boolean> = _isSessionSaved

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
            _isSessionSaved.postValue(true)
        }
    }

    fun postLogin(email: String, password: String) {
        viewModelScope.launch {
            repository.postLogin(email, password)
        }
    }
}