package com.example.mad_assignment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class StepCount(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "StepCounted") val stepcounted: String?,
)