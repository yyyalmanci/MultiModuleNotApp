package com.y3.data.local.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.y3.data.model.TaskDomainModel
import java.util.Date
import java.util.UUID

@Entity(tableName = TABLE_NAME_TASKS)
data class TaskEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var description: String = "",
    var tag: String = "",
    var imageUrl: String = "",
    var updatedAt: Date
)

fun TaskDomainModel.toTaskEntity() = TaskEntity(
    id, title, description, tag, imageUrl, updatedAt
)

const val TABLE_NAME_TASKS = "tasks"