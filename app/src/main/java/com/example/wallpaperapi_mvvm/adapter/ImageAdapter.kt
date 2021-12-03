package com.example.wallpaperapi_mvvm.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperapi_mvvm.R
import com.example.wallpaperapi_mvvm.models.Photo
import com.example.wallpaperapi_mvvm.view.ActivityDetails

class ImageAdapter(var context: Context,var imageList : MutableList<Photo>) :
    RecyclerView.Adapter<ImageAdapter.CardViewHolder>() {

    fun setPhoto(images : List<Photo>) {
        this.imageList = images.toMutableList()
        notifyDataSetChanged()
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.recycler_image_list_row,parent,false)

        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        Glide.with(context).load(imageList[position].urls.regular).into(holder.imageView)

        holder.imageView.setOnClickListener{
            val intent = Intent(context,ActivityDetails::class.java)
            intent.putExtra("image",imageList[position].urls.regular)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

}