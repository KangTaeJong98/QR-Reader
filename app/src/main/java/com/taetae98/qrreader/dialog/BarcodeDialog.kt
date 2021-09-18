package com.taetae98.qrreader.dialog

import androidx.navigation.fragment.navArgs
import com.taetae98.modules.library.navigation.NavigationDialogFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.DialogBarcodeBinding

class BarcodeDialog : NavigationDialogFragment<DialogBarcodeBinding>(R.layout.dialog_barcode) {
    private val args by navArgs<BarcodeDialogArgs>()
    private val barcode by lazy { args.barcode }
    private val format by lazy { args.format }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.barcode = barcode
        binding.format = format
    }
}