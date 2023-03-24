package com.example.ehhhhhh.presentation.adapters

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.R
import com.example.ehhhhhh.data.model.Dictionary
import com.example.ehhhhhh.data.model.Word
import com.example.ehhhhhh.databinding.WordsItemBinding
import java.util.*


class WordsAdapter(var words: MutableList<Word>, val c: Context): RecyclerView.Adapter<WordsAdapter.WordsViewHolder>(){

    private lateinit var tts: TextToSpeech
    inner class WordsViewHolder(val binding: WordsItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        val view = WordsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordsViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordsAdapter.WordsViewHolder, position: Int) {
        holder.binding.wordOrig.text = words[position].orig_word
        holder.binding.wordTranslate.text = words[position].translate
        holder.binding.wordTranscription.text = words[position].transcription

        holder.binding.wordVolume.setBackgroundResource(R.drawable.volume_animation)
        val volumeAnim = holder.binding.wordVolume.background as AnimationDrawable

        holder.binding.wordVolume.setOnClickListener{
            tts = TextToSpeech(c) {
                tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onDone(utteranceId: String) {
                        volumeAnim.stop()
                        volumeAnim.selectDrawable(0)
                    }
                    override fun onError(utteranceId: String) {
                    }
                    override fun onStart(utteranceId: String) {
                        volumeAnim.start()
                    }
                })
                if (it == TextToSpeech.SUCCESS){
                    tts.language = Locale.ENGLISH
                    tts.setSpeechRate(1.0f)
                    tts.speak(holder.binding.wordTranslate.text.toString(), TextToSpeech.QUEUE_ADD, null,
                        TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID)
                }
            }
        }
    }

    override fun getItemCount(): Int = words.size

    fun setData(newList: MutableList<Word>){
        words = newList
        notifyDataSetChanged()
    }

    fun getWord(position: Int): Word {
        return words[position]
    }

}