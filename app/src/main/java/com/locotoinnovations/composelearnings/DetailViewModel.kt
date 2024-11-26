package com.locotoinnovations.composelearnings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val detailId: String = savedStateHandle.get<String>(DETAIL_ID_SAVED_STATE_KEY)!!

    init {
        println(detailId)
    }

    companion object {
        private const val DETAIL_ID_SAVED_STATE_KEY = "detailId"
    }
}