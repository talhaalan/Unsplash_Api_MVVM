package com.example.wallpaperapi_mvvm.service

import com.example.wallpaperapi_mvvm.models.Photo
import com.example.wallpaperapi_mvvm.service.RetrofitInstance.Companion.API_KEY
import retrofit2.Call
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

    @Headers("Authorization: Client-ID $API_KEY")
    @GET("photos")
    fun getImages(
        @Query("page") page : Int, @Query("per_page") perPage : Int) : Call<List<Photo>>

}