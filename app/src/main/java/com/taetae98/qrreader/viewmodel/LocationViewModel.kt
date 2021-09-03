package com.taetae98.qrreader.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taetae98.qrreader.manager.SimpleLocationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val simpleLocationManager: SimpleLocationManager
): ViewModel() {
    val latitude by lazy { stateHandle.getLiveData("LOCATION_VIEW_MODEL_LATITUDE", "") }
    val longitude by lazy { stateHandle.getLiveData("LOCATION_VIEW_MODEL_LONGITUDE", "") }

    fun toBarcode(): String {
        return "geo:${latitude.value},${longitude.value}"
    }

    init {
        viewModelScope.launch {
            simpleLocationManager.getLastLocation()?.let {
                latitude.value = it.latitude.toString()
                longitude.value = it.longitude.toString()
            }
        }
    }
}