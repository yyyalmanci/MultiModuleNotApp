package com.y3.data.local.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.y3.common.EMPTY_STRING
import java.util.Date
import java.util.UUID

@Entity(tableName = TABLE_NAME_TASKS)
data class TaskEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    var title: String = EMPTY_STRING,
    var description: String = EMPTY_STRING,
    var tag: String = EMPTY_STRING,
    var imageUrl: String = EMPTY_STRING,
    var updatedAt: Date
)

const val TABLE_NAME_TASKS = "tasks"