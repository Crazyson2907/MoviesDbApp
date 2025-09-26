package com.task.moviesdbapp.data.local

import androidx.room.TypeConverter
import java.time.LocalDate

object DateConverters {
    @TypeConverter fun fromEpoch(epoch: Long?): LocalDate? = epoch?.let(LocalDate::ofEpochDay)
    @TypeConverter fun toEpoch(date: LocalDate?): Long? = date?.toEpochDay()
}