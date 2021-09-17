package com.taetae98.qrreader.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.util.Log
import android.widget.Toast
import androidx.print.PrintHelper
import com.google.android.material.imageview.ShapeableImageView
import com.google.zxing.BarcodeFormat
import com.taetae98.qrreader.R
import com.taetae98.qrreader.application.TAG
import com.taetae98.qrreader.application.toBarcode
import com.taetae98.qrreader.handler.BarcodeActionHandler
import com.taetae98.qrreader.manager.InternalStorageManager
import com.taetae98.qrreader.manager.SimpleClipboardManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BarcodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ShapeableImageView(context, attrs, defStyleAttr) {
    private val onLongClickItems by lazy {
        arrayOf(
            BarcodeViewActionItem(context.getString(R.string.copy_image)) {
                simpleClipboardManager.copyBarcode(context.getString(R.string.barcode),
                    barcode, format)
                Toast.makeText(context, R.string.finish, Toast.LENGTH_SHORT).show()
            },
            BarcodeViewActionItem(context.getString(R.string.copy_code)) {
                simpleClipboardManager.copyText(context.getString(R.string.code),
                    barcode
                )
                Toast.makeText(context, R.string.finish, Toast.LENGTH_SHORT).show()
            },
            BarcodeViewActionItem(context.getString(R.string.share_image)) {
                Intent(Intent.ACTION_SEND).apply {
                    type = "image/*"
                    putExtra(Intent.EXTRA_STREAM, internalStorageManager.saveBitmap(barcode.toBarcode(format)))
                }.also {
                    context.startActivity(Intent.createChooser(it, barcode))
                }
            },
            BarcodeViewActionItem(context.getString(R.string.share_code)) {
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/*"
                    putExtra(Intent.EXTRA_TEXT, barcode)
                }.also {
                    context.startActivity(Intent.createChooser(it, barcode))
                }
            },
            BarcodeViewActionItem(context.getString(R.string.print_image)) {
                PrintHelper(context).apply {
                    scaleMode = PrintHelper.SCALE_MODE_FIT
                }.also {
                    it.printBitmap(context.getString(R.string.code), barcode.toBarcode(format))
                }
            }
        )
    }

    @Inject
    lateinit var simpleClipboardManager: SimpleClipboardManager

    @Inject
    lateinit var internalStorageManager: InternalStorageManager

    var barcodeActionHandler: BarcodeActionHandler = BarcodeActionHandler.SimpleBarcodeActionHandler(context)

    var barcode = context.getString(R.string.app_url)
        set(value) {
            Log.d(TAG, "BarcodeView Barcode : $value")
            field = value
            setImageBitmap(value.toBarcode(format))
        }

    var format = BarcodeFormat.QR_CODE
        set(value) {
            field = value
            setImageBitmap(barcode.toBarcode(value))
        }


    init {
        setOnClickListener {
            barcodeActionHandler.action(barcode)
        }

        setOnLongClickListener {
            AlertDialog.Builder(context)
                .setItems(onLongClickItems.map { it.text }.toTypedArray()) { _, i ->
                    onLongClickItems[i].action.invoke()
                }.show()
            true
        }

        setImageBitmap(barcode.toBarcode(format))
    }

    data class BarcodeViewActionItem(
        val text: String,
        val action: () -> Unit
    )
}