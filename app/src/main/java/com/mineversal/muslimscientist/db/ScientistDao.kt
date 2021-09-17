package com.mineversal.muslimscientist.db

import androidx.room.*
import com.mineversal.muslimscientist.Scientist

@Dao
interface ScientistDao {

    @Insert
    fun insert(scientist: Scientist)

    @Update
    fun update(scientist: Scientist)

    @Delete
    fun delete(scientist: Scientist)

    @Query("SELECT * FROM scientist")
    fun getAll() : List<Scientist>

    @Query("SELECT * FROM scientist WHERE id = :id")
    fun getById(id: Int) : List<Scientist>
}