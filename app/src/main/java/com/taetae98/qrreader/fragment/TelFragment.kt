package com.taetae98.qrreader.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.taetae98.modules.library.navigation.NavigationFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentTelBinding
import com.taetae98.qrreader.interfaces.TabComponent
import com.taetae98.qrreader.viewmodel.BarcodeViewModel
import com.taetae98.qrreader.viewmodel.TelViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TelFragment : NavigationFragment<FragmentTelBinding>(R.layout.fragment_tel), TabComponent {
    override val tabIcon = R.drawable.ic_round_call_24

    private val telViewModel by activityViewModels<TelViewModel>()
    private val barcodeViewModel by viewModels<BarcodeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserveTel()
    }

    private fun onObserveTel() {
        telViewModel.tel.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = telViewModel.toBarcode()
        }
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.barcodeViewModel = barcodeViewModel
        binding.telViewModel = telViewModel
    }
}