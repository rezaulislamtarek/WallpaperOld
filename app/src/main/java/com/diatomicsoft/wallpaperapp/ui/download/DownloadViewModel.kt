package com.diatomicsoft.wallpaperapp.ui.download

import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(@ApplicationContext val context: Context) : ViewModel() {
    var filePath: ArrayList<String> = ArrayList()
    var filename: ArrayList<String> = ArrayList()
    private lateinit var listFile: Array<File>
    var file: File? = null

    init {
        loadFiles()
    }

    private fun loadFiles() {
        if (!Environment.getExternalStorageState().equals(Environment.DIRECTORY_PICTURES)) {
            Toast.makeText(context, "SD CARD NOT FOUND!", Toast.LENGTH_SHORT)

        } else {
            file =
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path.toString())
            if (!file!!.exists()) {
                file!!.mkdirs()
            }
        }
        filePath.clear()
        filename.clear()
        if (file!!.isDirectory) {
            listFile = file!!.listFiles()
            for (absolutePath in listFile) {
                filePath.add(absolutePath.absolutePath)
                filename.add(absolutePath.name)
            }
        }
    }
}