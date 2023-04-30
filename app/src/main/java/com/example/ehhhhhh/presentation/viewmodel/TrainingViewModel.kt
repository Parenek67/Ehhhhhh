package com.example.ehhhhhh.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ehhhhhh.data.dao.AppDatabase
import com.example.ehhhhhh.data.model.Training
import com.example.ehhhhhh.data.repository.TrainingRepository

class TrainingViewModel(context: Context): ViewModel(){

    private val trainRep: TrainingRepository

    init{
        val trainDao = AppDatabase.getDatabase(context).trainingDao()
        trainRep = TrainingRepository(trainDao)
    }

    suspend fun insertTrain(train: Training){
        trainRep.insert(train)
    }
}

class TrainViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrainingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TrainingViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}