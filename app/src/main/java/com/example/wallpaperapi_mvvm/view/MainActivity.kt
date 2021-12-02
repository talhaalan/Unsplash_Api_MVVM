package com.example.wallpaperapi_mvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wallpaperapi_mvvm.R
import com.example.wallpaperapi_mvvm.viewmodels.PhotoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var photoViewModel: PhotoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        photoViewModel = ViewModelProvider(this)[PhotoViewModel::class.java]

        photoViewModel.getLiveDataObserver().observe(this, Observer {
            if (it != null) {
                println("veri: " + it)
            }
            photoViewModel.makeApiCall()
        })


    }
}