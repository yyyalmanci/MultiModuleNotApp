package com.y3.domain

import com.y3.common.Resource
import com.y3.data.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: String): Resource<Int> =
        try {
            Resource.Success(taskRepository.deleteTaskById(taskId))
        } catch (t: Throwable) {
            Resource.Failure(t)
        }
}