package com.taetae98.qrreader.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QRViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : ViewModel() {
    val qr by lazy { stateHandle.getLiveData("QR", "Hello World") }
}