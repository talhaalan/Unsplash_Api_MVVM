package com.example.wallpaperapi_mvvm.repository

import com.example.wallpaperapi_mvvm.service.ApiInterface

class MainRepository constructor(private val apiInterface: ApiInterface) {

    suspend fun getAllPhoto() = apiInterface.getImages(4,60)
}