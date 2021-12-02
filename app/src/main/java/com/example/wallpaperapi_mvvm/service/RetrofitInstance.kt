package com.example.wallpaperapi_mvvm.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {

    companion object {

        const val BASE_URL = "https://api.unsplash.com/"
        const val API_KEY = "QYjZsHr97vrpdPaw9CQQpdm5BZDFiX0z76FNj77ZRIs"

        fun getInstance() : Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }


    }

}