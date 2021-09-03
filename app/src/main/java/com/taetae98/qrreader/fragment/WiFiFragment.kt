package com.taetae98.qrreader.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.taetae98.module.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentWifiBinding
import com.taetae98.qrreader.enums.WiFiEncryption
import com.taetae98.qrreader.interfaces.TabComponent
import com.taetae98.qrreader.viewmodel.BarcodeViewModel
import com.taetae98.qrreader.viewmodel.WiFiViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WiFiFragment : BindingFragment<FragmentWifiBinding>(R.layout.fragment_wifi), TabComponent {
    override val tabIcon = R.drawable.ic_round_wifi_24

    private val wifiViewModel by activityViewModels<WiFiViewModel>()
    private val barcodeViewModel by viewModels<BarcodeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserveSSID()
        onObserveEncryption()
        onObservePassword()
        onObserveHidden()
    }

    private fun onObserveSSID() {
        wifiViewModel.ssid.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = wifiViewModel.toBarcode()
        }
    }

    private fun onObserveEncryption() {
        wifiViewModel.encryption.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = wifiViewModel.toBarcode()
        }
    }

    private fun onObservePassword() {
        wifiViewModel.password.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = wifiViewModel.toBarcode()
        }
    }

    private fun onObserveHidden() {
        wifiViewModel.hidden.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = wifiViewModel.toBarcode()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateEncryptionInputLayout()

        return binding.root
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.viewModel = barcodeViewModel
        binding.wifiViewModel = wifiViewModel
    }

    private fun onCreateEncryptionInputLayout() {
        with(binding.encryptionInputLayout.editText as AutoCompleteTextView) {
            val values = WiFiEncryption.values()
            setAdapter(
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    values.map { it.name }
                )
            )

            setOnItemClickListener { _, _, i, _ ->
                wifiViewModel.encryption.value = values[i]
            }

            setText(wifiViewModel.encryption.value?.name, false)
        }
    }
}