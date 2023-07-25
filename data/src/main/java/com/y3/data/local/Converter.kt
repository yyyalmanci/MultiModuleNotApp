package com.y3.data.local

import androidx.room.TypeConverter
import java.util.*

class Converter {
    @TypeConverter
    fun toDate(timestamp: Long): Date {
        return java.sql.Date(timestamp)
    }

    @TypeConverter
    fun toTimeStamp(date: Date): Long {
        return date.time
    }
}