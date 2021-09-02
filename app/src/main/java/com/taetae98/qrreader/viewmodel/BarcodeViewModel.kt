package com.taetae98.qrreader.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BarcodeViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : ViewModel() {
    val barcode by lazy { stateHandle.getLiveData("BARCODE", " ") }
    val format by lazy { stateHandle.getLiveData("FORMAT", BarcodeFormat.QR_CODE) }
}