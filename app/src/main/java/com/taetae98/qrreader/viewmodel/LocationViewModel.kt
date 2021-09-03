package com.taetae98.qrreader.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    stateHandle: SavedStateHandle
): ViewModel() {
    val latitude by lazy { stateHandle.getLiveData("LOCATION_VIEW_MODEL_LATITUDE", "") }
    val longitude by lazy { stateHandle.getLiveData("LOCATION_VIEW_MODEL_LONGITUDE", "") }

    fun toBarcode(): String {
        return "geo:${latitude.value},${longitude.value}"
    }
}