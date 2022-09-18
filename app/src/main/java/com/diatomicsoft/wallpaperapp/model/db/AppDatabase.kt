package com.diatomicsoft.wallpaperapp.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.diatomicsoft.wallpaperapp.model.data.Wallpaper

@Database(entities = arrayOf(Wallpaper::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wallPaperDao(): WallpaperDao

    companion object{
        fun buildDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "wallpaperAppDb"
        ).build()
    }
}