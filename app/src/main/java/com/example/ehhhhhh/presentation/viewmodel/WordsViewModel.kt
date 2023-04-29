package com.example.ehhhhhh.presentation.viewmodel

import android.content.Context
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ehhhhhh.data.dao.AppDatabase
import com.example.ehhhhhh.data.model.Word
import com.example.ehhhhhh.data.repository.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.logging.Level

class WordsViewModel(context: Context, val dictName: String): ViewModel() {

    private val allWords: LiveData<MutableList<Word>>
    private val wordsRep: WordsRepository

    init {
        val wordsDao = AppDatabase.getDatabase(context).wordsDao()
        wordsRep = WordsRepository(wordsDao, dictName)
        allWords = wordsRep.wordsFromDict
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

    suspend fun getDictNames(): MutableList<String> {
        return wordsRep.getDictNames()
    }

    suspend fun getWordsByLevel(level: String): MutableList<Word>{
        val res = viewModelScope.async(Dispatchers.IO) {
            wordsRep.getWordsByLevel(level)
        }
        return res.await()
    }

    suspend fun getWordsForTrain(): MutableList<Word>{
        if(dictName.isDigitsOnly()){
            return getWordsByLevel(dictName)
        }
        else{
            return wordsRep.getWordsForTrain(dictName)
        }
    }

    suspend fun changeRepeatDate(origName: String, translate: String, repDate: String){
        wordsRep.changeRepeatDate(origName, translate, repDate)
    }

    suspend fun plusLevel(origName: String, translate: String){
        wordsRep.plusLevel(origName, translate)
    }

    suspend fun minusLevel(origName: String, translate: String){
        wordsRep.minusLevel(origName, translate)
    }
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