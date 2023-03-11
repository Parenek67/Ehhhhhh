package com.example.ehhhhhh.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ehhhhhh.data.model.Dictionary

@Dao
interface DictionaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dict: Dictionary)
    @Update
    suspend fun update(dict: Dictionary)
    @Delete
    suspend fun delete(dict: Dictionary)

    @Query("SELECT * FROM dictionary")
    fun selectAllDicts(): LiveData<MutableList<Dictionary>>
}