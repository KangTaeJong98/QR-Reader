package com.taetae98.qrreader.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.taetae98.module.binding.BindingFragment
import com.taetae98.qrreader.R
import com.taetae98.qrreader.application.TAG
import com.taetae98.qrreader.databinding.FragmentLocationBinding
import com.taetae98.qrreader.interfaces.TabComponent
import com.taetae98.qrreader.manager.SimpleLocationManager
import com.taetae98.qrreader.viewmodel.BarcodeViewModel
import com.taetae98.qrreader.viewmodel.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LocationFragment : BindingFragment<FragmentLocationBinding>(R.layout.fragment_location), TabComponent {
    override val tabIcon = R.drawable.ic_round_location_on_24

    private val locationViewModel by activityViewModels<LocationViewModel>()
    private val barcodeViewModel by viewModels<BarcodeViewModel>()

    private val onRequestLocationPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        for (entry in it) {
            if (!entry.value) {
                Log.d(TAG, "${entry.key} : ${entry.value}")
                return@registerForActivityResult
            }
        }

        requestMyLocation()
    }

    private var isLoadingLocation = false

    @Inject
    lateinit var simpleLocationManager: SimpleLocationManager

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateOnFloatingActionButton()

        return binding.root
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.locationViewModel = locationViewModel
        binding.barcodeViewModel = barcodeViewModel
        binding.setOnLocation {
            if (requireContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                requireContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                onRequestLocationPermission.launch(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                )
            } else {
                requestMyLocation()
            }
        }
    }

    private fun onCreateOnFloatingActionButton() {
        binding.setOnRefesh {
            binding.opened = !(binding.opened ?: false)
        }
    }

    private fun requestMyLocation() {
        if (isLoadingLocation) {
            return
        }

        val toast = Snackbar.make(binding.layout, R.string.loading_location, Snackbar.LENGTH_INDEFINITE).also {
            it.show()
        }
        isLoadingLocation = true

        simpleLocationManager.getUpdatedLocation {
            locationViewModel.latitude.value = it.latitude.toString()
            locationViewModel.longitude.value = it.longitude.toString()

            toast.dismiss()
            isLoadingLocation = false
        }
    }
}