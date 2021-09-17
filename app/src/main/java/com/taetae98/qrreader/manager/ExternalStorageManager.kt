package com.taetae98.qrreader.manager

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExternalStorageManager @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    companion object {
        fun getTempName(): String {
            return System.currentTimeMillis().toString()
        }
    }
    fun saveBitmap(name: String, bitmap: Bitmap): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        }

        return context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )?.also {
            context.contentResolver.openOutputStream(it)?.use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
        }
    }
}