package com.taetae98.qrreader.manager

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.taetae98.qrreader.R
import dagger.hilt.android.scopes.ActivityScoped
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@ActivityScoped
class InternalStorageManager @Inject constructor(
    private val activity: Activity
) {
    fun saveBitmap(bitmap: Bitmap): Uri {
        val file = File(activity.cacheDir, "${SimpleClipboardManager.QR_DIRECTORY}${File.pathSeparator}${System.currentTimeMillis()}.png").also {
            it.parentFile?.mkdirs()
        }

        FileOutputStream(file).also {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }

        return FileProvider.getUriForFile(activity, activity.getString(R.string.file_provider), file)
    }
}