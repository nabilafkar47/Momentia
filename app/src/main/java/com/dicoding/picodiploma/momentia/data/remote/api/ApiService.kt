package com.dicoding.picodiploma.momentia.data.remote.api

import com.dicoding.picodiploma.momentia.data.remote.model.AddStoryResponse
import com.dicoding.picodiploma.momentia.data.remote.model.LoginResponse
import com.dicoding.picodiploma.momentia.data.remote.model.RegisterResponse
import com.dicoding.picodiploma.momentia.data.remote.model.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): StoryResponse

    @GET("stories")
    fun getStoriesWithLocation(
        @Query("location") location: Int = 1,
    ): Call<StoryResponse>

    @Multipart
    @POST("stories")
    fun postStory(
        @Part photo: MultipartBody.Part?,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody?,
        @Part("lon") lon: RequestBody?
    ): Call<AddStoryResponse>
}