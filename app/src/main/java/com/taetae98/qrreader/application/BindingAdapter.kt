package com.taetae98.qrreader.application

import androidx.databinding.BindingAdapter
import com.google.zxing.BarcodeFormat
import com.taetae98.qrreader.view.BarcodeView

object BindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["barcode", "format"], requireAll = false)
    fun barcode(view: BarcodeView, barcode: String = " ", format: BarcodeFormat = BarcodeFormat.QR_CODE) {
        view.code = barcode
        view.format = format
    }
}