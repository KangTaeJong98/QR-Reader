package com.taetae98.qrreader.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.taetae98.module.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.FragmentLocationBinding
import com.taetae98.qrreader.interfaces.TabComponent
import com.taetae98.qrreader.viewmodel.BarcodeViewModel
import com.taetae98.qrreader.viewmodel.LocationViewModel

class LocationFragment : BindingFragment<FragmentLocationBinding>(R.layout.fragment_location), TabComponent {
    override val tabIcon = R.drawable.ic_round_location_on_24

    private val locationViewModel by activityViewModels<LocationViewModel>()
    private val barcodeViewModel by viewModels<BarcodeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserveLatitude()
        onObserveLongitude()
    }

    private fun onObserveLatitude() {
        locationViewModel.latitude.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = locationViewModel.toBarcode()
        }
    }

    private fun onObserveLongitude() {
        locationViewModel.longitude.observe(viewLifecycleOwner) {
            barcodeViewModel.barcode.value = locationViewModel.toBarcode()
        }
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.locationViewModel = locationViewModel
        binding.viewModel = barcodeViewModel
    }
}