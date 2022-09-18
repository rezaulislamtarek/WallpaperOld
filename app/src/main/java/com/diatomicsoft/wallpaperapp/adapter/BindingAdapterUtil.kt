package com.diatomicsoft.wallpaperapp.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("image")
fun setImage(view: ImageView, url: String ){
    val baseUrl = "https://iquote.diatomicsoft.com"
    Glide.with(view).load(baseUrl+url).into(view)
}