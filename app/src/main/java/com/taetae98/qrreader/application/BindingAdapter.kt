package com.taetae98.qrreader.application

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object BindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["qr"], requireAll = false)
    fun qr(view: ImageView, code: String? = null) {
        view.setImageBitmap(code?.toBarcode())
    }
}