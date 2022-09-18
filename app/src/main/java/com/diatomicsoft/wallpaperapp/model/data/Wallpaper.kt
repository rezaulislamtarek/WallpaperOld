package com.diatomicsoft.wallpaperapp.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wallpaper(
    val id: Int,
    val walpaper_url: String,
    val walpaper_name: String,
    val category_id: Int,
    val download: Int,
){
    @PrimaryKey(autoGenerate = true)
    var favId : Long = 0
}
