package com.example.ehhhhhh.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ehhhhhh.data.dao.AppDatabase
import com.example.ehhhhhh.data.model.Word
import com.example.ehhhhhh.data.repository.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordsViewModel(context: Context, dictName: String): ViewModel() {

    private val allWords: LiveData<MutableList<Word>>
    private val wordsRep: WordsRepository
    private val dictNames: LiveData<MutableList<String>>

    init {
        val wordsDao = AppDatabase.getDatabase(context).wordsDao()
        wordsRep = WordsRepository(wordsDao, dictName)
        allWords = wordsRep.wordsFromDict
        dictNames = wordsRep.dictNames
    }

    fun insertWord(word: Word){
        viewModelScope.launch(Dispatchers.IO) {
            wordsRep.insert(word)
        }
    }

    fun deleteWord(word: Word){
        viewModelScope.launch(Dispatchers.IO) {
            wordsRep.delete(word)
        }
    }

    fun changeCount(dictName: String, count: Int){
        viewModelScope.launch(Dispatchers.IO) {
            wordsRep.changeCount(dictName, count)
        }
    }

    fun getWordsFromDict() = allWords

    fun getDictNames() = dictNames
}

class WordsViewModelFactory(private val context: Context, val dictName: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordsViewModel(context, dictName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}