package com.taetae98.qrreader.manager

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.google.zxing.BarcodeFormat
import com.taetae98.qrreader.R
import com.taetae98.qrreader.application.toBarcode
import dagger.hilt.android.scopes.ActivityScoped
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@ActivityScoped
class SimpleClipboardManager @Inject constructor(
    private val activity: Activity
) {
    private val manager by lazy { activity.getSystemService(ClipboardManager::class.java) }

    companion object {
        const val QR_DIRECTORY = "qr"
    }

    fun copyText(label: String = "", text: String = "") {
        manager.setPrimaryClip(
            ClipData.newPlainText(label, text)
        )
    }

    fun copyBarcode(label: String = "", code: String = "", formant: BarcodeFormat = BarcodeFormat.QR_CODE): Uri {
        return saveBitmap(code.toBarcode(formant)).also {
            manager.setPrimaryClip(
                ClipData.newUri(
                    activity.contentResolver, label, it
                )
            )
        }
    }

    private fun saveBitmap(bitmap: Bitmap): Uri {
        val file = File(activity.cacheDir, "$QR_DIRECTORY${File.pathSeparator}${System.currentTimeMillis()}.png").also {
            it.parentFile?.mkdirs()
        }

        FileOutputStream(file).also {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }

        return FileProvider.getUriForFile(activity, activity.getString(R.string.file_provider), file)
    }
}