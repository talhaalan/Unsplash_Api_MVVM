package com.example.wallpaperapi_mvvm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperapi_mvvm.R
import com.example.wallpaperapi_mvvm.adapter.ImageAdapter
import com.example.wallpaperapi_mvvm.databinding.ActivityMainBinding
import com.example.wallpaperapi_mvvm.models.Photo
import com.example.wallpaperapi_mvvm.models.Search
import com.example.wallpaperapi_mvvm.repository.MainRepository
import com.example.wallpaperapi_mvvm.service.ApiInterface
import com.example.wallpaperapi_mvvm.service.RetrofitInstance
import com.example.wallpaperapi_mvvm.viewmodels.PhotoViewModel
import com.example.wallpaperapi_mvvm.viewmodels.ViewModelFactory
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var photoViewModel: PhotoViewModel
    private lateinit var binding : ActivityMainBinding
    private var adapter : ImageAdapter? = null
    lateinit var list : MutableList<Photo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        list = mutableListOf()
        adapter = ImageAdapter(this, list)

        binding.recyclerView.layoutManager = GridLayoutManager(this,2)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        val retrofitInstance = RetrofitInstance.getInstance()
        val mainRepository = MainRepository(retrofitInstance)

        photoViewModel = ViewModelProvider(this,ViewModelFactory(mainRepository)).get(PhotoViewModel::class.java)

        photoViewModel.photoList.observe(this, Observer {
            adapter?.setPhoto(it)
            for (i in it) {
                println("list: " + i.urls.full)
            }
        })

        photoViewModel.isLoading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            }else {
                binding.progressBar.visibility = View.GONE
            }
        })
        photoViewModel.getAll()

    }

}