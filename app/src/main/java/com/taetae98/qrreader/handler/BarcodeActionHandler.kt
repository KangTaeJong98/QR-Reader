package com.taetae98.qrreader.handler

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.taetae98.qrreader.R
import com.taetae98.qrreader.application.TAG
import com.taetae98.qrreader.enums.InternetProtocol
import com.taetae98.qrreader.enums.WiFiEncryption

abstract class BarcodeActionHandler {
    fun action(barcode: String) {
        try {
            Log.d(TAG, "Action Barcode : $barcode")

            val scheme = barcode.substringBefore(":")
            when {
                scheme.equals("https", true) || scheme.equals("http", true) -> {
                    onInternet(barcode)
                }
                scheme.equals("wifi", true) -> {
                    onWiFi(barcode)
                }
                scheme.equals("geo", true) -> {
                    onLocation(barcode)
                }
                scheme.equals("tel", true) -> {
                    onTel(barcode)
                }
                scheme.equals("smsto", true) -> {
                    onMessage(barcode)
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
    protected abstract fun onInternet(barcode: String)
    protected abstract fun onWiFi(barcode: String)
    protected abstract fun onLocation(barcode: String)
    protected abstract fun onTel(barcode: String)
    protected abstract fun onMessage(barcode: String)

    open class SimpleBarcodeActionHandler(
        private val context: Context
    ) : BarcodeActionHandler() {
        override fun onNothing(barcode: String) {
            Toast.makeText(context, barcode, Toast.LENGTH_SHORT).show()
        }

        override fun onInternet(barcode: String) {
            context.startActivity(Intent.createChooser(
                Intent(Intent.ACTION_VIEW, Uri.parse(barcode)),
                context.getString(R.string.internet)
            ))
        }

        override fun onWiFi(barcode: String) {
            context.startActivity(Intent.createChooser(
                Intent(Settings.ACTION_WIFI_SETTINGS),
                context.getString(R.string.wifi)
            ))
        }

        override fun onLocation(barcode: String) {
            context.startActivity(Intent.createChooser(
                Intent(Intent.ACTION_VIEW, Uri.parse(barcode)),
                context.getString(R.string.location)
            ))
        }

        override fun onTel(barcode: String) {
            context.startActivity(Intent.createChooser(
                Intent(Intent.ACTION_DIAL, Uri.parse(barcode)),
                context.getString(R.string.tel)
            ))
        }

        override fun onMessage(barcode: String) {
            val uri = barcode.substringBeforeLast(":")
            val message = barcode.substringAfterLast(":")
            context.startActivity(Intent.createChooser(
                Intent(Intent.ACTION_SEND, Uri.parse(uri)).apply {
                    putExtra("sms_body", message)
                },
                context.getString(R.string.message)
            ))
        }
    }
}