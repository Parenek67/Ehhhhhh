package com.example.ehhhhhh.data.repository

import androidx.lifecycle.LiveData
import com.example.ehhhhhh.data.dao.WordsDao
import com.example.ehhhhhh.data.model.Word

class WordsRepository(val wordsDao: WordsDao, dictName: String) {

    val wordsFromDict = wordsDao.getWordsFromDict(dictName)

    suspend fun insert(word: Word){
        wordsDao.insert(word)
    }

    suspend fun delete(word: Word){
        wordsDao.delete(word)
    }

    suspend fun changeCount(dictName: String, count: Int){
        wordsDao.changeWordCount(dictName, count)
    }

    fun getWordsByLevel(level: String): MutableList<Word> {
        return wordsDao.getWordsByLevel(level)
    }

    suspend fun getDictNames(): MutableList<String>{
        return wordsDao.getDictNames()
    }

    fun getWordsForTrain(dictName: String): MutableList<Word>{
        return wordsDao.getWordsForTrain(dictName)
    }
}