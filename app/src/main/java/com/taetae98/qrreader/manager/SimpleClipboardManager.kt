package com.taetae98.qrreader.manager

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.net.Uri
import com.google.zxing.BarcodeFormat
import com.taetae98.qrreader.application.toBarcode
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class SimpleClipboardManager @Inject constructor(
    private val activity: Activity,
    private val internalStorageManager: InternalStorageManager
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

    fun copyBarcode(label: String = "", barcode: String = "", formant: BarcodeFormat = BarcodeFormat.QR_CODE): Uri {
        return internalStorageManager.saveBitmap(barcode.toBarcode(formant)).also {
            manager.setPrimaryClip(
                ClipData.newUri(
                    activity.contentResolver, label, it
                )
            )
        }
    }
}