package com.diatomicsoft.wallpaperapp.repository

import com.diatomicsoft.wallpaperapp.model.data.Wallpaper
import com.diatomicsoft.wallpaperapp.model.db.WallpaperDao
import javax.inject.Inject

class FavRepository @Inject constructor(private val dao: WallpaperDao) {
    suspend fun getWallpapers(): List<Wallpaper> =  dao.getWallpapers()
}