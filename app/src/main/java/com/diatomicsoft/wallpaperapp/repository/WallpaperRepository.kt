package com.diatomicsoft.wallpaperapp.repository

import com.diatomicsoft.wallpaperapp.model.data.Wallpaper
import com.diatomicsoft.wallpaperapp.model.db.WallpaperDao
import com.diatomicsoft.wallpaperapp.model.network.Api
import javax.inject.Inject

class WallpaperRepository @Inject constructor(
    val api: Api,
    private val dao: WallpaperDao
    )  {
    suspend fun getImagesByCategory(id: Int): List<Wallpaper> {
        return api.getImagesByCategoryId(id).body()!!
    }
    suspend fun addFavouriteWallpaper(wallpaper: Wallpaper):Long = dao.insertWallpaper(wallpaper)
    suspend fun getAllFavouriteWallpapers() = dao.getWallpapers()
    suspend fun deleteFromFavourite(id:Long) = dao.deleteWallpaper(id)
}