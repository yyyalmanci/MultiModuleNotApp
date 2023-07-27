package com.y3.domain.model

import com.y3.common.EMPTY_STRING
import com.y3.data.local.entitiy.TaskEntity
import java.util.Date
import java.util.UUID


data class TaskDomainModel(
    val id: String = UUID.randomUUID().toString(),
    var title: String = EMPTY_STRING,
    var description: String = EMPTY_STRING,
    var tag: String = EMPTY_STRING,
    var imageUrl: String = EMPTY_STRING,
    var updatedAt: Date
)

fun TaskEntity.taskDomainModel() = TaskDomainModel(
    id, title, description, tag, imageUrl, updatedAt
)

fun TaskDomainModel.taskTaskEntity() = TaskEntity(
    id, title, description, tag, imageUrl, updatedAt
)


