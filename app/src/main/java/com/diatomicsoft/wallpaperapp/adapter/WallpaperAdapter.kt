package com.diatomicsoft.wallpaperapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.diatomicsoft.wallpaperapp.R
import com.diatomicsoft.wallpaperapp.databinding.RvImageItemBinding
import com.diatomicsoft.wallpaperapp.model.data.Wallpaper
import com.diatomicsoft.wallpaperapp.util.WallpaperItemClickListener


class WallpaperAdapter(val data: List<Wallpaper>, val listener: WallpaperItemClickListener): RecyclerView.Adapter<WallpaperAdapter.RecViewHolder>() {
   inner class RecViewHolder (val binding: RvImageItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder {
        val binding: RvImageItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_image_item,
            parent, false
        )
        return RecViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecViewHolder, position: Int) {
        holder.binding.item   = data[position]
        holder.binding.root.setOnClickListener {
            //listener.itemClickListener(it,data[position])

        }
        holder.binding.fav.setOnClickListener{
            // Fav button Click
            listener.addFavClick(data[position])
        }

        holder.binding.useAsWallpaper.setOnClickListener {
            listener.setWallPaperClick(data[position])
        }
        holder.binding.download.setOnClickListener {
            listener.onDownLoadClick(data[position])
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

}