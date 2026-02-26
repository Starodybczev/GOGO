package com.example.prac_five.googlemap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prac_five.fireBase.FirestoreService
import com.example.prac_five.googlemap.model.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class MapUiState {
    object Idle : MapUiState()
    object Loading : MapUiState()
    data class Success(val places: List<Place>) : MapUiState()
    data class Error(val message: String) : MapUiState()
}

class MapViewModel : ViewModel() {

    private val _state = MutableStateFlow<MapUiState>(MapUiState.Idle)
    val state = _state.asStateFlow()

    fun loadPlaces() {
        viewModelScope.launch {
            _state.value = MapUiState.Loading

            val result = FirestoreService.FirestoreService.getPlaces()

            _state.value = result.fold(
                onSuccess = { MapUiState.Success(it) },
                onFailure = { MapUiState.Error(it.message ?: "Failed to load places") }
            )
        }
    }
}