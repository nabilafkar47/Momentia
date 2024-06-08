package com.dicoding.picodiploma.momentia.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.momentia.data.StoryRepository
import com.dicoding.picodiploma.momentia.data.remote.model.RegisterResponse
import com.dicoding.picodiploma.momentia.utils.Event
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: StoryRepository) : ViewModel() {

    val alertMessage: LiveData<Event<String>> = repository.alertMessage
    val isLoading: LiveData<Boolean> = repository.isLoading
    val registerResponse: LiveData<RegisterResponse> = repository.registerResponse

    fun postRegister(name: String, email: String, password: String) {
        viewModelScope.launch {
            repository.postRegister(name, email, password)
        }
    }
}