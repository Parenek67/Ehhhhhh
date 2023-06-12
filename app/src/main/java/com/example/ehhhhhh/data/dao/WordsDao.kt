package com.example.ehhhhhh.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
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
    @Query("SELECT name FROM dictionary")
    suspend fun getDictNames(): MutableList<String>
    @Query("SELECT * FROM word WHERE level = :level LIMIT 30")
    fun getWordsByLevel(level:  String): MutableList<Word>

    @Query("SELECT * FROM word WHERE dict_name = :dict_name")
    fun getWordsForTrain(dict_name: String): MutableList<Word>

    @Query("UPDATE word SET rep_date = :rep_date WHERE orig_word = :orig_name AND translate = :translate")
    suspend fun setRepeatDate(orig_name: String, translate: String, rep_date: String)

    @Query("UPDATE word SET level = level + 1 WHERE orig_word = :orig_name AND translate = :translate")
    suspend fun plusLevel(orig_name: String, translate: String)

    @Query("UPDATE word SET level = level - 1 WHERE orig_word = :orig_name AND translate = :translate AND level>0")
    suspend fun minusLevel(orig_name: String, translate: String)
}