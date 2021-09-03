package com.taetae98.qrreader.handler

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.taetae98.qrreader.R
import java.net.URI

abstract class BarcodeActionHandler {
    fun action(barcode: String) {
        try {
            val uri = URI.create(barcode)
            when {
                uri.scheme.equals("http", true) || uri.scheme.equals("https", true) -> {
                    onInternet(Uri.parse(barcode))
                }
            }
        } catch (e: Exception) {
            onNothing(barcode)
        }
    }

    protected abstract fun onNothing(barcode: String)
    protected abstract fun onInternet(uri: Uri)

    class SimpleBarcodeActionHandler(
        private val context: Context
    ) : BarcodeActionHandler() {
        override fun onNothing(barcode: String) {

        }

        override fun onInternet(uri: Uri) {
            context.startActivity(Intent.createChooser(
                Intent(Intent.ACTION_VIEW, uri),
                context.getString(R.string.internet)
            ))
        }
    }
}