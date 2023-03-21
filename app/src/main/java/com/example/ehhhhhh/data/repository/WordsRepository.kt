package com.example.ehhhhhh.data.repository

import com.example.ehhhhhh.data.WordsDao
import com.example.ehhhhhh.data.model.Dictionary
import com.example.ehhhhhh.data.model.Word

class WordsRepository(val wordsDao: WordsDao, dictName: String) {

    val wordsFromDict = wordsDao.getWordsFromDict(dictName)

    suspend fun insert(word: Word){
        wordsDao.insert(word)
    }
}