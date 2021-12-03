package com.example.wallpaperapi_mvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.wallpaperapi_mvvm.R
import com.example.wallpaperapi_mvvm.databinding.ActivityDetailsBinding

class ActivityDetails : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        val image : String? = intent.getStringExtra("image")

        Glide.with(this).load(image).into(binding.fullImage)

    }
}