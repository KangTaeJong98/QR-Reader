package com.taetae98.qrreader.manager

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.taetae98.qrreader.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternalStorageManager @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    companion object {
        const val QR_PATH = "qr"

        fun getTempName(): String {
            return System.currentTimeMillis().toString()
        }
    }

    fun saveBitmap(path: String, name: String, bitmap: Bitmap): Uri {
        val file = File("${context.cacheDir}/$path", "$name.png").also {
            it.parentFile?.mkdirs()
        }

        FileOutputStream(file).also {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }

        return FileProvider.getUriForFile(context, context.getString(R.string.file_provider), file)
    }
}