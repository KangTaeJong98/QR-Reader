package com.taetae98.qrreader.application

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

const val TAG = "QR_LOG"
const val DATABASE_NAME = "barcode.db"

fun String.toBarcode(format: BarcodeFormat = BarcodeFormat.QR_CODE, width: Int = 500, height: Int = 500): Bitmap {
    return try {
        BarcodeEncoder().encodeBitmap(toByteArray().toString(Charsets.ISO_8859_1), format, width, height)
    } catch (e: Exception) {
        Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
    }
}