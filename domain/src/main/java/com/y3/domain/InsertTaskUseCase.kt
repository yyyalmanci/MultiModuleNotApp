package com.y3.domain

import com.y3.domain.model.TaskDomainModel
import com.y3.data.repository.TaskRepository
import com.y3.domain.model.taskTaskEntity
import javax.inject.Inject

class InsertTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskDomainModel: TaskDomainModel) {
        taskRepository.insertTask(taskDomainModel.taskTaskEntity())
    }
}