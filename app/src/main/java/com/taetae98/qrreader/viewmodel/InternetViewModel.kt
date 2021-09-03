package com.taetae98.qrreader.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.taetae98.qrreader.enums.InternetProtocol
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InternetViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : ViewModel() {
    val protocol by lazy { stateHandle.getLiveData("INTERNET_VIEW_MODEL_PROTOCOL", InternetProtocol.HTTP) }
    val address by lazy { stateHandle.getLiveData("INTERNET_VIEW_MODEL_ADDRESS", "") }

    fun toBarcode(): String {
        return "${protocol.value?.value}://${address.value}"
    }
}