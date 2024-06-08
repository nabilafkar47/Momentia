package com.dicoding.picodiploma.momentia.utils

import android.content.Context
import com.dicoding.picodiploma.momentia.data.StoryRepository
import com.dicoding.picodiploma.momentia.data.pref.UserPreference
import com.dicoding.picodiploma.momentia.data.pref.dataStore
import com.dicoding.picodiploma.momentia.data.remote.api.ApiConfig
import com.dicoding.picodiploma.momentia.data.database.StoryDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val token = runBlocking { pref.getToken().first() }
        val apiService = ApiConfig.getApiService(token.toString())
        val database = StoryDatabase.getDatabase(context)
        return StoryRepository.getInstance(pref, apiService, database)
    }
}