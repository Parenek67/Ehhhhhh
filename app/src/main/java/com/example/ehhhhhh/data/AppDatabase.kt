package com.example.ehhhhhh.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ehhhhhh.data.model.Dictionary
import com.example.ehhhhhh.data.model.Word

@Database(entities = [Dictionary::class, Word::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun dictionaryDao(): DictionaryDao

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