package com.taetae98.qrreader.handler

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.taetae98.qrreader.R
import com.taetae98.qrreader.application.TAG

abstract class BarcodeActionHandler {
    fun action(barcode: String) {
        try {
            Log.d(TAG, "Action Barcode : $barcode")
            val scheme = barcode.substringBefore(":")
            Log.d(TAG, "Action Scheme : $scheme")
            when {
                scheme.equals("https", true) -> {
                    onInternet(Uri.parse(barcode))
                }
                scheme.equals("wifi", true) -> {
                    onWiFi(Uri.parse(barcode))
                }
                scheme.equals("geo", true) -> {
                    onLocation(Uri.parse(barcode))
                }
                else -> {
                    onNothing(barcode)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Action Error : ", e)
            onNothing(barcode)
        }
    }

    protected abstract fun onNothing(barcode: String)
    protected abstract fun onInternet(uri: Uri)
    protected abstract fun onWiFi(uri: Uri)
    protected abstract fun onLocation(uri: Uri)

    open class SimpleBarcodeActionHandler(
        private val context: Context
    ) : BarcodeActionHandler() {
        override fun onNothing(barcode: String) {
            Toast.makeText(context, barcode, Toast.LENGTH_SHORT).show()
        }

        override fun onInternet(uri: Uri) {
            context.startActivity(Intent.createChooser(
                Intent(Intent.ACTION_VIEW, uri),
                context.getString(R.string.internet)
            ))
        }

        override fun onWiFi(uri: Uri) {
            context.startActivity(Intent.createChooser(
                Intent(Settings.ACTION_WIFI_SETTINGS),
                context.getString(R.string.wifi)
            ))
        }

        override fun onLocation(uri: Uri) {
            context.startActivity(Intent.createChooser(
                Intent(Intent.ACTION_VIEW, uri),
                context.getString(R.string.location)
            ))
        }
    }
}