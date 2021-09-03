package com.taetae98.qrreader.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TelViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : ViewModel() {
    val tel by lazy { stateHandle.getLiveData("TEL_VIEW_MODEL_TEL", "") }

    fun toBarcode(): String {
        return "tel:${tel.value}"
    }
}