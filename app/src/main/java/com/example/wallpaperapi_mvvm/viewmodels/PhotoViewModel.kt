package com.example.wallpaperapi_mvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wallpaperapi_mvvm.models.Photo
import com.example.wallpaperapi_mvvm.service.ApiInterface
import com.example.wallpaperapi_mvvm.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoViewModel : ViewModel() {

    lateinit var liveDataPhoto : MutableLiveData<List<Photo>>

    init {
        liveDataPhoto = MutableLiveData()
    }

    fun getLiveDataObserver() : MutableLiveData<List<Photo>> {
        return liveDataPhoto
    }

    fun makeApiCall() {
        val retrofitInstance = RetrofitInstance.getInstance()
        val retrofitService = retrofitInstance.create(ApiInterface::class.java)
        val call = retrofitService.getImages(1,30)
        call.enqueue(object : Callback<List<Photo>> {
            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                liveDataPhoto.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                //liveDataPhoto.postValue(null)
            }

        })
    }



}