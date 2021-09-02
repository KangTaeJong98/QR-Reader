package com.taetae98.qrreader.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.widget.Toast
import androidx.print.PrintHelper
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import com.google.zxing.BarcodeFormat
import com.taetae98.qrreader.R
import com.taetae98.qrreader.application.toBarcode
import com.taetae98.qrreader.manager.InternalStorageManager
import com.taetae98.qrreader.manager.SimpleClipboardManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BarcodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {
    private val imageView by lazy { ShapeableImageView(context, attrs, defStyleAttr) }
    private val actionItems by lazy {
        arrayOf(
            BarcodeViewItem(context.getString(R.string.copy_image)) {
                simpleClipboardManager.copyBarcode(context.getString(R.string.barcode),
                    code, format)
                Toast.makeText(context, R.string.finish, Toast.LENGTH_SHORT).show()
            },
            BarcodeViewItem(context.getString(R.string.copy_code)) {
                simpleClipboardManager.copyText(context.getString(R.string.code),
                    code
                )
                Toast.makeText(context, R.string.finish, Toast.LENGTH_SHORT).show()
            },
            BarcodeViewItem(context.getString(R.string.share_image)) {
                Intent(Intent.ACTION_SEND).apply {
                    type = "image/*"
                    putExtra(Intent.EXTRA_STREAM, internalStorageManager.saveBitmap(code.toBarcode(format)))
                }.also {
                    context.startActivity(Intent.createChooser(it, code))
                }
            },
            BarcodeViewItem(context.getString(R.string.share_code)) {
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/*"
                    putExtra(Intent.EXTRA_TEXT, code)
                }.also {
                    context.startActivity(Intent.createChooser(it, code))
                }
            },
            BarcodeViewItem(context.getString(R.string.print_code)) {
                PrintHelper(context).apply {
                    scaleMode = PrintHelper.SCALE_MODE_FIT
                }.also {
                    it.printBitmap(context.getString(R.string.code), code.toBarcode(format))
                }
            }
        )
    }

    @Inject
    lateinit var simpleClipboardManager: SimpleClipboardManager

    @Inject
    lateinit var internalStorageManager: InternalStorageManager

    var code = " "
        set(value) {
            field = value
            imageView.setImageBitmap(value.toBarcode(format))
        }

    var format = BarcodeFormat.QR_CODE
        set(value) {
            field = value
            imageView.setImageBitmap(code.toBarcode(value))
        }


    init {
        addView(
            imageView,
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        )

        setOnClickListener {

        }

        setOnLongClickListener {
            AlertDialog.Builder(context)
                .setItems(actionItems.map { it.text }.toTypedArray()) { _, i ->
                    actionItems[i].action.invoke()
                }.show()
            true
        }

        imageView.setImageBitmap(code.toBarcode(format))
    }

    data class BarcodeViewItem(
        val text: String,
        val action: () -> Unit
    )
}