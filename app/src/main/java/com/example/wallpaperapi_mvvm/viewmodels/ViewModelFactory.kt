package com.example.wallpaperapi_mvvm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wallpaperapi_mvvm.repository.MainRepository
import java.lang.IllegalArgumentException

class ViewModelFactory constructor(private val repository: MainRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
            PhotoViewModel(this.repository) as T
        }else {
            throw IllegalArgumentException("Not found")
        }
    }
}