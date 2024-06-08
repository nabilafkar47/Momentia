package com.dicoding.picodiploma.momentia.view.addstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.momentia.data.StoryRepository
import com.dicoding.picodiploma.momentia.data.remote.model.AddStoryResponse
import com.dicoding.picodiploma.momentia.utils.Event
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val repository: StoryRepository) : ViewModel() {

    val alertMessage: LiveData<Event<String>> = repository.alertMessage
    val isLoading: LiveData<Boolean> = repository.isLoading
    val addStoryResponse: LiveData<AddStoryResponse> = repository.addStoryResponse

    fun postStory(
        photo: MultipartBody.Part?,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ) {
        viewModelScope.launch {
            repository.postStory(photo, description, lat, lon)
        }
    }
}