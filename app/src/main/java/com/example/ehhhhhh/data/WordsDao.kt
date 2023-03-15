package com.example.ehhhhhh.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ehhhhhh.data.model.Word

@Dao
interface WordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Query("SELECT * FROM word WHERE dict_name = :dict_name")
    fun getWordsFromDict(dict_name: String): LiveData<MutableList<Word>>
}