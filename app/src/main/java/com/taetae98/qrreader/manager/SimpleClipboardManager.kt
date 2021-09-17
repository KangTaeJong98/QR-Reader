package com.taetae98.qrreader.manager

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import com.google.zxing.BarcodeFormat
import com.taetae98.qrreader.application.toBarcode
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SimpleClipboardManager @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val internalStorageManager: InternalStorageManager
) {
    private val manager by lazy { context.getSystemService(ClipboardManager::class.java) }

    fun copyText(label: String = "", text: String = "") {
        manager.setPrimaryClip(
            ClipData.newPlainText(label, text)
        )
    }

    fun copyUri(uri: Uri = Uri.EMPTY, label: String = "") {
        manager.setPrimaryClip(
            ClipData.newUri(context.contentResolver, label, uri)
        )
    }

    fun copyBarcode(label: String = "", barcode: String = "", formant: BarcodeFormat = BarcodeFormat.QR_CODE): Uri {
        return internalStorageManager.saveBitmap(InternalStorageManager.QR_PATH, InternalStorageManager.getTempName(), barcode.toBarcode(formant)).also {
            copyUri(it, label)
        }
    }
}