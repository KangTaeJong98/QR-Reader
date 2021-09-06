package com.taetae98.qrreader.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor(
    stateHandle: SavedStateHandle
): ViewModel() {
    val email by lazy { stateHandle.getLiveData("EMAIL_VIEW_MODEL_EMAIL", "") }
    val title by lazy { stateHandle.getLiveData("EMAIL_VIEW_MODEL_TITLE", "")}
    val content by lazy { stateHandle.getLiveData("EMAIL_VIEW_MODEL_content", "")}

    fun toBarcode(): String {
        return "MATMSG:TO:${email.value};SUB:${title.value};BODY:${content.value};;"
    }
}