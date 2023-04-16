package com.example.ehhhhhh.data.repository

import com.example.ehhhhhh.data.dao.WordsDao
import com.example.ehhhhhh.data.model.Word

class WordsRepository(val wordsDao: WordsDao, dictName: String) {

    val wordsFromDict = wordsDao.getWordsFromDict(dictName)
    val dictNames = wordsDao.getDictNames()

    suspend fun insert(word: Word){
        wordsDao.insert(word)
    }

    suspend fun delete(word: Word){
        wordsDao.delete(word)
    }

    suspend fun changeCount(dictName: String, count: Int){
        wordsDao.changeWordCount(dictName, count)
    }
}