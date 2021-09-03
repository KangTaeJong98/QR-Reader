package com.taetae98.qrreader.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InternetViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        val PROTOCOLS by lazy { arrayOf("HTTP", "HTTPS") }
    }

    val protocol by lazy { stateHandle.getLiveData("INTERNET_VIEW_MODEL_PROTOCOL", PROTOCOLS.first()) }
    val address by lazy { stateHandle.getLiveData("INTERNET_VIEW_MODEL_ADDRESS", "") }
}