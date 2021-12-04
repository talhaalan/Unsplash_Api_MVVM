package com.example.wallpaperapi_mvvm.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.wallpaperapi_mvvm.R
import com.example.wallpaperapi_mvvm.databinding.ActivityDetailsBinding
import java.text.SimpleDateFormat

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

        getUserDetails()

    }

    private fun getUserDetails() {
        val altDescription : String? = intent.getStringExtra("alt_description")

        if (altDescription.isNullOrEmpty()) {
            binding.details.text = "Resim detayı yok."
        } else {
            binding.details.text = altDescription
        }

        val createdAt : String? = intent.getStringExtra("created_at")

        val dateFormatInput = "yyyy-MM-dd'T'HH:mm:ss"
        val dateFormatOutput = "yyyy-MM-dd"


        val formatInput = SimpleDateFormat(dateFormatInput)
        val formatOutput = SimpleDateFormat(dateFormatOutput)

        val date = formatInput.parse(createdAt)
        val dateString = formatOutput.format(date)

        if (!createdAt.isNullOrEmpty()) {
            binding.createdAt.text = "Yüklenme tarihi: " + dateString
        }

        val userName = intent.getStringExtra("user_name")
        if (!userName.isNullOrEmpty()) {
            binding.userName.text = userName.toString()
        }

        val userImage = intent.getStringExtra("user_profile_image")
        val imageUri : Uri? = Uri.parse(userImage)
        if (!userImage.isNullOrEmpty()) {
            Glide.with(this).load(imageUri).into(binding.imageUser)
            //binding.imageUser.setImageURI(imageUri)
        }

        val userBio = intent.getStringExtra("user_bio")

        if (!userBio.isNullOrEmpty()) {
            binding.userBio.text = "Bio: " + userBio
        } else {
            binding.userBio.text = "Bio: Boş"
        }

    }

}