package com.example.ehhhhhh.data.repository

import com.example.ehhhhhh.data.WordsDao
import com.example.ehhhhhh.data.model.Dictionary
import com.example.ehhhhhh.data.model.Word

class WordsRepository(val wordsDao: WordsDao, dict: Dictionary) {

    val wordsFromDict = wordsDao.getWordsFromDict(dict.name)

    suspend fun insert(word: Word){
        wordsDao.insert(word)
    }
}