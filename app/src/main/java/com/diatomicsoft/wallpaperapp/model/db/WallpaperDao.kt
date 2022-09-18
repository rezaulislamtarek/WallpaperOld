package com.diatomicsoft.wallpaperapp.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.diatomicsoft.wallpaperapp.model.data.Wallpaper

@Dao
interface WallpaperDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWallpaper(wallpaper: Wallpaper): Long

    @Query("SELECT * FROM wallpaper ORDER BY favId DESC")
    fun getWallpapers(): List<Wallpaper>

    @Query("DELETE FROM wallpaper WHERE favId = :id")
    fun deleteWallpaper(id:Long)
}