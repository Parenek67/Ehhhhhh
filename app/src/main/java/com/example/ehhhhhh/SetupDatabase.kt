package com.example.ehhhhhh

import android.util.Log
import com.example.ehhhhhh.data.model.Dictionary
import com.example.ehhhhhh.data.model.Word
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SetupDatabase(/*val words: MutableList<Word>*/) {
    private lateinit var database: DatabaseReference

    fun createData(){
        database = FirebaseDatabase.getInstance().getReference("Dictionary")
        val dict = Dictionary("Еда",10, "а вот так", "07.05.2023", false)
        database.child("Еда").setValue(dict).addOnSuccessListener{
            Log.d("fff", "ok")
        }.addOnFailureListener{
            Log.d("fff", it.message.toString())
        }
        /*words.forEach{
            database.child(it.orig_word).setValue(it).addOnSuccessListener{
                Log.d("fff", "ok")
            }.addOnFailureListener{
                Log.d("fff", it.message.toString())
            }
        }*/
    }
}