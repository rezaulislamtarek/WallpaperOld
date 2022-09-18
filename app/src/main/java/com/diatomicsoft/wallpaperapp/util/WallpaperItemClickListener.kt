package com.diatomicsoft.wallpaperapp.util

import com.diatomicsoft.wallpaperapp.model.data.Wallpaper

interface WallpaperItemClickListener {
    fun addFavClick(wallpaper: Wallpaper)
    fun onDownLoadClick(wallpaper: Wallpaper)
    fun setWallPaperClick(wallpaper: Wallpaper)
}