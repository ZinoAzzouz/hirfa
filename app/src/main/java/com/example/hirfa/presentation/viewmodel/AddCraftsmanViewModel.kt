package com.example.hirfa.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hirfa.domain.usecase.AddCraftsmanUseCase
import com.example.hirfa.data.model.Craftsman
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCraftsmanViewModel @Inject constructor(
    private val addCraftsmanUseCase: AddCraftsmanUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CraftsmanState())
    val state: StateFlow<CraftsmanState> = _state

    fun onEvent(event: AddCraftsmanEvent) {
        when (event) {
            is AddCraftsmanEvent.NameChanged -> _state.value = _state.value.copy(name = event.name)
            is AddCraftsmanEvent.DescriptionChanged -> _state.value = _state.value.copy(description = event.description)
            is AddCraftsmanEvent.CategorySelected -> _state.value = _state.value.copy(category = event.category)
            is AddCraftsmanEvent.PhoneNumberChanged -> _state.value = _state.value.copy(phoneNumber = event.phoneNumber)
            is AddCraftsmanEvent.ProfilePictureChanged -> _state.value = _state.value.copy(profilePicture = event.uri)
            is AddCraftsmanEvent.Submit -> submitCraftsman()
        }
    }

    private fun submitCraftsman() {
        val currentState = _state.value

        if (currentState.name.isBlank() || currentState.category.isBlank() || currentState.phoneNumber.isBlank()) {
            _state.value = currentState.copy(errorMessage = "الرجاء ملء جميع الحقول المطلوبة")
            return
        }

        viewModelScope.launch {
            _state.value = currentState.copy(isLoading = true)

            val craftsman = Craftsman(
                id = "",
                name = currentState.name,
                description = currentState.description,
                category = currentState.category,
                profilePicture = currentState.profilePicture?.toString() ?: "",
                urlImage = "",
                phoneNumber = currentState.phoneNumber,
                rating = 0f,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )

            val result = addCraftsmanUseCase(craftsman)

            _state.value = _state.value.copy(
                isLoading = false,
                successMessage = if (result) "تمت الإضافة بنجاح" else "حدث خطأ أثناء الإضافة"
            )
        }
    }

    sealed class AddCraftsmanEvent {
        data class NameChanged(val name: String) : AddCraftsmanEvent()
        data class DescriptionChanged(val description: String) : AddCraftsmanEvent()
        data class PhoneNumberChanged(val phoneNumber: String) : AddCraftsmanEvent()
        data class ProfilePictureChanged(val uri: Uri) : AddCraftsmanEvent()
        data class CategorySelected(val category: String) : AddCraftsmanEvent()
        object Submit : AddCraftsmanEvent()
    }
}

data class CraftsmanState(
    val name: String = "",
    val description: String = "",
    val category: String = "",
    val phoneNumber: String = "",
    val profilePicture: Uri? = null,
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)
