package com.taetae98.qrreader.fragment

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.print.PrintHelper
import com.google.zxing.integration.android.IntentIntegrator
import com.taetae98.lib.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.application.TAG
import com.taetae98.qrreader.application.toBarcode
import com.taetae98.qrreader.databinding.FragmentScanBinding
import com.taetae98.qrreader.viewmodel.QRViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class ScanFragment : BindingFragment<FragmentScanBinding>(R.layout.fragment_scan) {
    private val qrViewModel by viewModels<QRViewModel>()

    private val onScanResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        Log.d(TAG, it.toString())
        when(it.resultCode) {
            Activity.RESULT_OK -> {
                IntentIntegrator.parseActivityResult(it.resultCode, it.data).also { result ->
                    qrViewModel.qr.value = result.contents
                }
            }
        }
    }

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_scan_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.share -> {
                onShare()
                true
            }
            R.id.print -> {
                onPrint()
                true
            }
            else -> {
                false
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()

        return binding.root
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        with(binding) {
            code = qrViewModel.qr
            setOnScan { onScan() }
            setOnQR { onQR() }
            setOnCode { onCode() }
        }
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onScan() {
        onScanResult.launch(IntentIntegrator.forSupportFragment(this).apply {
            setPrompt("")
            setBeepEnabled(false)
        }.createScanIntent())
    }

    private fun onQR() {
        val file = File(requireContext().cacheDir, "/qr/${System.currentTimeMillis()}.png").apply {
            parentFile?.mkdirs()
        }
        FileOutputStream(file).apply {
            qrViewModel.qr.value!!.toBarcode().compress(Bitmap.CompressFormat.PNG, 100, this)
        }

        val uri = FileProvider.getUriForFile(requireContext(),"FileProvider", file)
        getSystemService(ClipboardManager::class.java).setPrimaryClip(ClipData.newUri(requireContext().contentResolver, "QR", uri))
    }

    private fun onCode() {
        getSystemService(ClipboardManager::class.java).setPrimaryClip(ClipData.newPlainText("QR", qrViewModel.qr.value))
        Toast.makeText(requireContext(), "Copy", Toast.LENGTH_SHORT).show()
    }

    private fun onPrint() {
        PrintHelper(requireContext()).apply {
            scaleMode = PrintHelper.SCALE_MODE_FIT
        }.also {
            it.printBitmap("Code", qrViewModel.qr.value!!.toBarcode())
        }
    }

    private fun onShare() {
        Intent(Intent.ACTION_SEND).apply {
            type = "text/*"
            putExtra(Intent.EXTRA_TEXT, qrViewModel.qr.value)
        }.also {
            startActivity(Intent.createChooser(it, qrViewModel.qr.value))
        }
    }
}