package com.example.mad_assignment

import androidx.room.*

@Dao
interface StepCountDaoDao {
    @Query("SELECT * FROM student_table")
    fun getAll(): List<StepCount>

    @Query("SELECT * FROM student_table")
    fun data(steps: Int): StepCount


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(StepCount: StepCount)


}