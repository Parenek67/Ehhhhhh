package com.example.ehhhhhh.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ehhhhhh.data.model.Dictionary
import com.example.ehhhhhh.data.model.Word

@Dao
interface WordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)
    @Delete
    suspend fun delete(word: Word)
    @Query("SELECT * FROM word WHERE dict_name = :dict_name")
    fun getWordsFromDict(dict_name: String): LiveData<MutableList<Word>>
    @Query("UPDATE dictionary SET words = words + :count WHERE name = :dict_name")
    suspend fun changeWordCount(dict_name: String, count: Int)
}