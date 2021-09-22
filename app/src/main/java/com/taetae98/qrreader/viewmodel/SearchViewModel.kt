package com.taetae98.qrreader.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : ViewModel() {
    val search by lazy { stateHandle.getLiveData("SEARCH_VIEW_MODEL_SEARCH", "") }
}