package com.taetae98.qrreader.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.taetae98.qrreader.repository.BarcodeDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BarcodeDataViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : ViewModel() {
    @Inject
    lateinit var barcodeDataRepository: BarcodeDataRepository

    val barcodeData by lazy { barcodeDataRepository.findAllLiveData() }
    val bookmarkedBarcodeData by lazy { barcodeDataRepository.findIsBookmarked() }

    suspend fun insert(barcode: String, format: BarcodeFormat = BarcodeFormat.QR_CODE) {
        barcodeDataRepository.insert(barcode, format)
    }

    suspend fun deleteByIds(collection: Collection<Long>): Int {
        return barcodeDataRepository.deleteByIds(collection)
    }
}