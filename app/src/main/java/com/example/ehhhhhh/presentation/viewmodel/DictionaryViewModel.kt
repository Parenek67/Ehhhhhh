package com.example.ehhhhhh.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ehhhhhh.data.AppDatabase
import com.example.ehhhhhh.data.model.Dictionary
import com.example.ehhhhhh.data.repository.DictionaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DictionaryViewModel(context: Context): ViewModel() {

    private val allDicts: LiveData<MutableList<Dictionary>>
    private val dictsRep: DictionaryRepository
    private var wordCount = 0

    init {
        val dictDao = AppDatabase.getDatabase(context).dictionaryDao()
        dictsRep = DictionaryRepository(dictDao)
        allDicts = dictsRep.allDicts
    }

    fun insertDict(dict: Dictionary){
        viewModelScope.launch(Dispatchers.IO) {
            dictsRep.insert(dict)
        }
    }

    fun deleteDict(dict: Dictionary){
        viewModelScope.launch(Dispatchers.IO) {
            dictsRep.delete(dict)
        }
    }

    fun getAllDicts() = allDicts
}

class DictViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DictionaryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DictionaryViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}