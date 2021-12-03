package com.example.wallpaperapi_mvvm.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wallpaperapi_mvvm.models.Photo
import com.example.wallpaperapi_mvvm.models.Search
import com.example.wallpaperapi_mvvm.repository.MainRepository
import com.example.wallpaperapi_mvvm.service.ApiInterface
import com.example.wallpaperapi_mvvm.service.RetrofitInstance
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    val photoList = MutableLiveData<List<Photo>>()
    val isLoading = MutableLiveData<Boolean>()
    var job : Job? = null

    fun getAll() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = mainRepository.getAllPhoto()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    photoList.postValue(response.body())
                    isLoading.value = false
                }
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}