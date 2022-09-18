package com.diatomicsoft.wallpaperapp.ui.image

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.diatomicsoft.wallpaperapp.model.data.Wallpaper
import com.diatomicsoft.wallpaperapp.repository.WallpaperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject


@HiltViewModel
class WallpaperViewModel @Inject constructor(
    val repo: WallpaperRepository,
    @ApplicationContext val context: Context
) : ViewModel() {
    var wallpaperList = MutableLiveData<List<Wallpaper>>()
    val baseUrl = "https://iquote.diatomicsoft.com"
    fun getWallPapers(id: Int) {
        viewModelScope.launch {
            wallpaperList.value = repo.getImagesByCategory(id)
        }
    }

    fun addToFav(wallpaper: Wallpaper) {
        CoroutineScope(Dispatchers.IO).launch {
            val res = repo.addFavouriteWallpaper(wallpaper)
            Log.d("ResponseFromDB", res.toString())
        }
    }

    fun useAsWallpaper(wallpaper: Wallpaper) {
        CoroutineScope(Dispatchers.IO).launch {

            var wallpaperManager: WallpaperManager = WallpaperManager.getInstance(context)
            var backgroundImage =
                Glide.with(context).asBitmap().load(baseUrl + wallpaper.walpaper_url).submit().get()
            wallpaperManager.setBitmap(backgroundImage)
        }
    }

    fun downloadWallpaper(wallpaper: Wallpaper) {
        Glide.with(context)
            .asBitmap()
            .load(baseUrl + wallpaper.walpaper_url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    saveMediaToStorage(resource)
                }
            })
    }

    private fun saveMediaToStorage(bitmap: Bitmap) {
        val filename = " ${context.packageName.split('.')[2]}+${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")

                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES,)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            //These for devices running on android < Q
            //So I don't think an explanation is needed here
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            //Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            toast("Saved to Photos")
        }
    }

    private fun toast(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }
}