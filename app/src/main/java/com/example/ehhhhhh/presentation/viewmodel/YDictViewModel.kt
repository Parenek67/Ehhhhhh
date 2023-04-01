package com.example.ehhhhhh.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ehhhhhh.data.model.ydictapi.YDictResponse
import com.example.ehhhhhh.data.repository.YDictRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class YDictViewModel(val rep: YDictRepository): ViewModel() {

    val response: MutableLiveData<Response<YDictResponse>> = MutableLiveData()

    fun getWord(key: String, lang: String, text: String){
        viewModelScope.launch {
            response.value = rep.getWord(key, lang, text)
        }
    }
}

class YDictViewModelFactory(val rep: YDictRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return YDictViewModel(rep) as T
    }
}