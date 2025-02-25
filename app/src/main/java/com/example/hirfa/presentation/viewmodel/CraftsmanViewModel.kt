package com.example.hirfa.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hirfa.data.model.Craftsman
import com.example.hirfa.data.repository.CraftsmanRepository
import com.example.hirfa.domain.usecase.GetCraftsmenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UIState(
    val isLoading: Boolean = false,
    val craftsmen: List<Craftsman> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class CraftsmanViewModel @Inject constructor(private val getCraftsmenUseCase: GetCraftsmenUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    init {
        loadServices()
    }

    private fun loadServices() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            getCraftsmenUseCase().catch {
                _uiState.value = _uiState.value.copy(isLoading = false, craftsmen = emptyList(), error = "Failed to get Craftsmen")
            }.collect{ craftsmen ->

                _uiState.value = _uiState.value.copy(isLoading = false,craftsmen = craftsmen,error=null)

            }
        }
    }
}
