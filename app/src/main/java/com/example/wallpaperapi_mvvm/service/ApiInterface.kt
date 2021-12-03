package com.example.wallpaperapi_mvvm.service

import com.example.wallpaperapi_mvvm.models.Photo
import com.example.wallpaperapi_mvvm.models.Search
import com.example.wallpaperapi_mvvm.service.RetrofitInstance.Companion.API_KEY
import com.example.wallpaperapi_mvvm.service.RetrofitInstance.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {


    @GET("photos")
    @Headers("Authorization: Client-ID $API_KEY")
    suspend fun getImages(
        @Query("page") page : Int, @Query("per_page") perPage : Int) : Response<List<Photo>>
}