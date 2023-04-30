package com.example.ehhhhhh.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "training")
data class Training(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val type: String,
    val true_answers: Int,
    val false_answers: Int
)
