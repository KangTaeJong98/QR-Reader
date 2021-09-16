package com.taetae98.qrreader.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.taetae98.modules.library.navigation.NavigationFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentInternetBinding
import com.taetae98.qrreader.enums.InternetProtocol
import com.taetae98.qrreader.interfaces.TabComponent
import com.taetae98.qrreader.viewmodel.BarcodeViewModel
import com.taetae98.qrreader.viewmodel.InternetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InternetFragment : NavigationFragment<FragmentInternetBinding>(R.layout.fragment_internet), TabComponent {
    override val tabIcon = R.drawable.ic_round_public_24

    private val internetViewModel by activityViewModels<InternetViewModel>()
    private val barcodeViewModel by viewModels<BarcodeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserveProtocol()
        onObserveAddress()
    }

    private fun onObserveProtocol() {
        internetViewModel.protocol.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = internetViewModel.toBarcode()
        }
    }

    private fun onObserveAddress() {
        internetViewModel.address.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = internetViewModel.toBarcode()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateProtocolInputLayout()

        return binding.root
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.barcodeViewModel = barcodeViewModel
        binding.internetViewModel = internetViewModel
    }

    private fun onCreateProtocolInputLayout() {
        with(binding.protocolInputLayout.editText as AutoCompleteTextView) {
            val values = InternetProtocol.values()
            setAdapter(
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    values.map { it.name }
                )
            )

            setOnItemClickListener { _, _, i, _ ->
                internetViewModel.protocol.value = values[i]
            }

            setText(internetViewModel.protocol.value?.name, false)
        }
    }
}