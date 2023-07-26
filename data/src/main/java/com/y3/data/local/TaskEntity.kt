package com.y3.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date
import java.util.UUID

@Entity(tableName = TABLE_NAME_TASKS)
@Parcelize
data class TaskEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var description: String = "",
    var tag: String = "",
    var imageUrl: String = "",
    var updatedAt: Date
) : Parcelable

const val TABLE_NAME_TASKS = "tasks"