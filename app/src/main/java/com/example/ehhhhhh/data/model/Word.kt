package com.example.ehhhhhh.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(foreignKeys = [ForeignKey(entity = Dictionary::class,
                        parentColumns = ["name"],
                        childColumns = ["dict_name"],
                        onDelete = CASCADE)],
        primaryKeys = ["dict_name","orig_word"],
        tableName = "word")
data class Word(
    val dict_name: String,
    val orig_word: String,
    val translate: String,
    val transcription: String,
    val level: Int
)
