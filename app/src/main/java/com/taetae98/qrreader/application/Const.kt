package com.taetae98.qrreader.application

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

const val TAG = "QR_LOG"

fun String.toBarcode(format: BarcodeFormat = BarcodeFormat.QR_CODE, width: Int = 500, height: Int = 500): Bitmap {
    return BarcodeEncoder().encodeBitmap(this, format, width, height)
}