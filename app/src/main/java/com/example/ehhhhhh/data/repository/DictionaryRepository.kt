package com.example.ehhhhhh.data.repository

import com.example.ehhhhhh.data.dao.DictionaryDao
import com.example.ehhhhhh.data.model.Dictionary

class DictionaryRepository(val dictDao: DictionaryDao) {

    val allDicts = dictDao.selectAllDicts()

    suspend fun insert(dict: Dictionary){
        dictDao.insert(dict)
    }

    suspend fun delete(dict: Dictionary){
        dictDao.delete(dict)
    }
}