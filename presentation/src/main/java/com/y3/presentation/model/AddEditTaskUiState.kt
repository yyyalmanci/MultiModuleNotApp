package com.y3.presentation.model

import androidx.annotation.StringRes

sealed class AddEditTaskUiState {
    object SuccessDelete : AddEditTaskUiState()
    object SuccessAdd : AddEditTaskUiState()
    class Failure(@StringRes text: Int) : AddEditTaskUiState()
    object Idle : AddEditTaskUiState()
    object Loading : AddEditTaskUiState()
}