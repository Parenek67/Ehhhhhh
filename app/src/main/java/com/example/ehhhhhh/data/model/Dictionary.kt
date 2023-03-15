package com.example.ehhhhhh.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary")
data class Dictionary(
    @PrimaryKey
    val name: String,
    val words: Int,
    val description: String,
    val date: String,
    val isFavourite: Boolean
)
