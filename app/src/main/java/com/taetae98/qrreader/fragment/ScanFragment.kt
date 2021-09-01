package com.taetae98.qrreader.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.print.PrintHelper
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.integration.android.IntentIntegrator
import com.taetae98.lib.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.application.TAG
import com.taetae98.qrreader.application.toBarcode
import com.taetae98.qrreader.databinding.FragmentScanBinding
import com.taetae98.qrreader.manager.SimpleClipboardManager
import com.taetae98.qrreader.viewmodel.QRViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ScanFragment : BindingFragment<FragmentScanBinding>(R.layout.fragment_scan) {
    private val qrViewModel by viewModels<QRViewModel>()

    private val onScanResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        when(it.resultCode) {
            Activity.RESULT_OK -> {
                IntentIntegrator.parseActivityResult(it.resultCode, it.data).also { result ->
                    qrViewModel.qr.value = result.contents
                }
            }
        }
    }

    @Inject
    lateinit var simpleClipboardManager: SimpleClipboardManager

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
        simpleClipboardManager.copyBarcode(
            getString(R.string.qr),
            qrViewModel.qr.value!!
        )

        Snackbar.make(binding.coordinatorLayout, getString(R.string.copy), Snackbar.LENGTH_SHORT).show()
    }

    private fun onCode() {
        simpleClipboardManager.copyText(
            getString(R.string.qr),
            qrViewModel.qr.value!!
        )

        Snackbar.make(binding.coordinatorLayout, getString(R.string.copy), Snackbar.LENGTH_SHORT).show()
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