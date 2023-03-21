package com.example.ehhhhhh.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.data.model.Word
import com.example.ehhhhhh.databinding.WordsItemBinding

class WordsAdapter(var words: MutableList<Word>): RecyclerView.Adapter<WordsAdapter.WordsViewHolder>(){

    inner class WordsViewHolder(val binding: WordsItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        val view = WordsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordsViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordsAdapter.WordsViewHolder, position: Int) {
        holder.binding.wordOrig.text = words[position].orig_word
        holder.binding.wordTranslate.text = words[position].translate
        holder.binding.wordTranscription.text = words[position].transcription
    }

    override fun getItemCount(): Int = words.size

    fun setData(newList: MutableList<Word>){
        words = newList
        notifyDataSetChanged()
    }

}