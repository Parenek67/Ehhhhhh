package com.example.ehhhhhh.data.repository

import com.example.ehhhhhh.data.dao.TrainingDao
import com.example.ehhhhhh.data.model.Training

class TrainingRepository(val trainDao: TrainingDao) {

    suspend fun insert(train: Training){
        trainDao.insert(train)
    }
}