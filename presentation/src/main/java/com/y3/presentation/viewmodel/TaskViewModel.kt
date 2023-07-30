package com.y3.presentation.viewmodel

import androidx.lifecycle.*
import com.y3.common.Resource
import com.y3.domain.DeleteTaskUseCase
import com.y3.domain.GetTasksUseCase
import com.y3.domain.InsertTaskUseCase
import com.y3.domain.model.TaskDomainModel
import com.y3.presentation.R
import com.y3.presentation.model.AddEditTaskUiState
import com.y3.presentation.model.TasksUiState
import com.y3.presentation.model.enums.ActionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val tasksUseCase: GetTasksUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val insertTaskUseCase: InsertTaskUseCase
) : ViewModel() {

    private var _tasks = MutableStateFlow<TasksUiState>(TasksUiState.Idle)
    val tasks = _tasks.asStateFlow()

    private var _taskEdit = MutableStateFlow<AddEditTaskUiState>(AddEditTaskUiState.Idle)
    val taskEdit = _taskEdit.asStateFlow()

    private var _taskDelete = MutableStateFlow<AddEditTaskUiState>(AddEditTaskUiState.Idle)
    val taskDelete = _taskDelete.asStateFlow()

    lateinit var taskModel: TaskDomainModel

    fun loadTask() {
        viewModelScope.launch {
            _tasks.value = TasksUiState.Loading
            when (val resourceTasks = tasksUseCase().first()) {
                is Resource.Success -> {
                    _tasks.value = TasksUiState.Success(resourceTasks.data)
                }
                is Resource.Failure -> {
                    Timber.d("failure", resourceTasks.throwable.message.toString())
                }

            }
        }
    }

    fun isEditMode(actionType: ActionType) = actionType == ActionType.EDIT

    fun setTaskEntityItem(taskModel: TaskDomainModel, actionType: ActionType) {
        if (isEditMode(actionType)) {
            this.taskModel = taskModel
        }
    }

    fun deleteTaskItem(taskId: String) {
        viewModelScope.launch {
            _taskDelete.value = AddEditTaskUiState.Loading
            when (val resourceDeleteAction = deleteTaskUseCase(taskId)) {
                is Resource.Success -> {
                    _taskDelete.value = AddEditTaskUiState.SuccessDelete
                }
                is Resource.Failure -> {
                    Timber.d("failure", resourceDeleteAction.throwable.message.toString())
                }
            }
        }
    }

    // we use insert function for update event too because room is run with replecable
    fun insertTaskItem(taskModel: TaskDomainModel) {
        when {
            taskModel.description.isEmpty() || taskModel.title.isEmpty() -> {
                _taskEdit.value = AddEditTaskUiState.Failure(R.string.empty_fields_warning)
            }
            else -> {
                viewModelScope.launch {
                    _taskDelete.value = AddEditTaskUiState.Loading
                    when (val resourceDeleteAction = insertTaskUseCase(taskModel)) {
                        is Resource.Success -> {
                            _taskEdit.value = AddEditTaskUiState.SuccessAdd
                        }
                        is Resource.Failure -> {
                            Timber.d("failure", resourceDeleteAction.throwable.message.toString())
                        }
                    }
                }
            }
        }
    }
}