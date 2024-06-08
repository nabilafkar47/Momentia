package com.dicoding.picodiploma.momentia.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.picodiploma.momentia.data.StoryRepository
import com.dicoding.picodiploma.momentia.data.pref.UserModel
import com.dicoding.picodiploma.momentia.data.remote.model.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: StoryRepository) : ViewModel() {

    val story: LiveData<PagingData<ListStoryItem>> =
        repository.getAllStories().cachedIn(viewModelScope)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}