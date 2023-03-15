package com.example.ehhhhhh.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.data.model.Word
import com.example.ehhhhhh.databinding.WordsItemBinding

class WordsAdapter(var words: MutableList<Word>): RecyclerView.Adapter<WordsAdapter.WordsViewHolder>(){

    inner class WordsViewHolder(val binding: WordsItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: WordsAdapter.WordsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}