package com.taetae98.qrreader.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.taetae98.qrreader.enums.WiFiEncryption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WiFiViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : ViewModel(){
    val ssid by lazy { stateHandle.getLiveData("WIFI_VIEW_MODEL_SSID", "") }
    val encryption by lazy { stateHandle.getLiveData("WIFI_VIEW_MODEL_ENCRYPTION", WiFiEncryption.NONE) }
    val password by lazy { stateHandle.getLiveData("WIFI_VIEW_MODEL_PASSWORD", "") }
    val hidden by lazy { stateHandle.getLiveData("WIFI_VIEW_MODEL_HIDDEN", false) }

    fun toBarcode(): String {
        return "WIFI:S:${ssid.value};T:${encryption.value?.value};P:${if (encryption.value == WiFiEncryption.NONE) "" else password.value};H:${hidden.value};;"
    }
}