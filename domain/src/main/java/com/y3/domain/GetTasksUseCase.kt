package com.y3.domain

import com.y3.common.Resource
import com.y3.data.model.TaskDomainModel
import com.y3.data.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke():  Flow<Resource<List<TaskDomainModel>>> =
        taskRepository.observeTasks()
}