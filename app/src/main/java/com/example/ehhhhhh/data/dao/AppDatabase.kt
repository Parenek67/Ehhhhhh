package com.example.ehhhhhh.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ehhhhhh.data.model.Dictionary
import com.example.ehhhhhh.data.model.Training
import com.example.ehhhhhh.data.model.Word

@Database(entities = [Dictionary::class, Word::class, Training::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun dictionaryDao(): DictionaryDao
    abstract fun wordsDao(): WordsDao
    abstract fun trainingDao(): TrainingDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}