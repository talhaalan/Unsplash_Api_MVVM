package com.example.wallpaperapi_mvvm.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {

    companion object {

        const val BASE_URL = "https://api.unsplash.com/"
        const val API_KEY = "QYjZsHr97vrpdPaw9CQQpdm5BZDFiX0z76FNj77ZRIs"

        var apiInterface : ApiInterface? = null
        fun getInstance() : ApiInterface {
            if (apiInterface == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                apiInterface = retrofit.create(ApiInterface::class.java)
            }
            return apiInterface!!
        }

    }

}