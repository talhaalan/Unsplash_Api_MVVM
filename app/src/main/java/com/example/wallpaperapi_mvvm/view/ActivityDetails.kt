package com.example.wallpaperapi_mvvm.view

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.wallpaperapi_mvvm.R
import com.example.wallpaperapi_mvvm.databinding.ActivityDetailsBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import java.io.File
import java.lang.Exception
import java.util.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class ActivityDetails : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    var uuid = UUID.randomUUID().toString()
    var job : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        val image : String? = intent.getStringExtra("image")

        Glide.with(this).load(image).into(binding.fullImage)

        getUserDetails()

        var downloadUrl : String? = intent.getStringExtra("downloadUrl")
        binding.downloadButton.setOnClickListener {
            if (isWriteStoragePermissionGranted() && isReadStoragePermissionGranted()) {
                if (downloadUrl != null) {
                    download()
                }
            }


        }

        var wallpaperManager : WallpaperManager = WallpaperManager.getInstance(applicationContext)

        binding.setButton.setOnClickListener {
            val bitmap = (binding.fullImage.drawable as BitmapDrawable).bitmap
            wallpaperManager.setBitmap(bitmap)
            Snackbar.make(it, "Ayarlandı", Snackbar.LENGTH_SHORT).show()
        }

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
    fun downloadImage(filename : String, downloadUrlImage: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                try {

                    //binding.progressBar.visibility = View.VISIBLE
                    //binding.textViewDownloadMessage.visibility = View.VISIBLE

                    val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                    val downloadUri = Uri.parse(downloadUrlImage)
                    val request = DownloadManager.Request(downloadUri)
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle(filename)
                        .setMimeType("image/jpeg")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_PICTURES,
                            File.separator + filename + ".jpg"
                        )


                    dm.enqueue(request)

                    val cursor = dm.query(DownloadManager.Query().setFilterByStatus(DownloadManager.STATUS_PAUSED
                            or DownloadManager.STATUS_PENDING
                            or DownloadManager.STATUS_RUNNING or DownloadManager.STATUS_SUCCESSFUL))

                    if (cursor != null && cursor.moveToFirst()) {
                        val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

                        cursor.close()

                        when (status) {
                            DownloadManager.STATUS_RUNNING -> {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.textViewDownloadMessage.visibility = View.VISIBLE
                            }
                        }

                        println("status: " + status)

                    }

                    var onComplete: BroadcastReceiver = object : BroadcastReceiver() {
                        override fun onReceive(context: Context?, intent: Intent?) {
                            binding.textViewDownloadMessage.visibility = View.INVISIBLE
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                    }
                    registerReceiver(onComplete, IntentFilter(
                        DownloadManager.ACTION_DOWNLOAD_COMPLETE)
                    )

                    //Toast.makeText(this@SetWallpaperActivity, "Başladı...", Toast.LENGTH_SHORT).show()

                } catch (e: Exception) {
                    Toast.makeText(this@ActivityDetails, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun download() = runBlocking {

        var downloadUrl : String? = intent.getStringExtra("downloadUrl")
        downloadImage(uuid, downloadUrl!!)
        binding.progressBar.visibility = View.VISIBLE
        binding.textViewDownloadMessage.visibility = View.VISIBLE
    }
    fun isReadStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    3
                )
                false
            }
        } else {
            true
        }
    }

    fun isWriteStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    2
                )
                false
            }
        } else {
            true
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        var downloadUrl : String? = intent.getStringExtra("downloadUrl")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {

            2 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (downloadUrl != null) {
                        download()
                    }
                } else {

                }
            }
        }
    }

}