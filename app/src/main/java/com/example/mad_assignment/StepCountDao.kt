package com.example.mad_assignment

import androidx.room.*

@Dao
interface StepCountDaoDao {
    @Query("SELECT * FROM steptable")
    fun getAll(): List<StepCount>

    @Query("SELECT * FROM steptable WHERE Date LIKE :data LIMIT 1")
    fun findbydate(data: String): StepCount


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stepcount: StepCount)

    @Query("UPDATE steptable SET StepCounted = :step WHERE Date LIKE :data")
    fun update(step: String, data: String)

}

//from https://github.com/foxandroid/RoomDB