package com.taetae98.qrreader.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.google.zxing.BarcodeFormat
import com.taetae98.qrreader.dto.BarcodeData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BarcodeViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
) : ViewModel() {
    constructor(stateHandle: SavedStateHandle, barcodeData: BarcodeData) : this(stateHandle) {
        name.value = barcodeData.name
        barcode.value = barcodeData.barcode
        format.value = barcodeData.format
    }

    val name by lazy { stateHandle.getLiveData("BARCODE_VIEW_MODEL_NAME", "") }
    val barcode by lazy { stateHandle.getLiveData("BARCODE_VIEW_MODEL_BARCODE", "") }
    val format by lazy { stateHandle.getLiveData("BARCODE_VIEW_MODEL_FORMAT", BarcodeFormat.QR_CODE) }

    class Factory(
        private val barcodeData: BarcodeData,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null
    ) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return BarcodeViewModel(handle, barcodeData) as T
        }
    }
}