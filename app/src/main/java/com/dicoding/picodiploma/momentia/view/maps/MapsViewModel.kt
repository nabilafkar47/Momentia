package com.dicoding.picodiploma.momentia.view.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.momentia.data.StoryRepository
import com.dicoding.picodiploma.momentia.data.remote.model.ListStoryItem
import com.dicoding.picodiploma.momentia.utils.Event
import kotlinx.coroutines.launch

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {

    val alertMessage: LiveData<Event<String>> = repository.alertMessage
    val isLoading: LiveData<Boolean> = repository.isLoading
    val stories: LiveData<List<ListStoryItem>> = repository.stories

    fun getStoriesWithLocation() {
        viewModelScope.launch {
            repository.getStoriesWithLocation()
        }
    }
}