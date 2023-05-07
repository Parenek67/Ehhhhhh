package com.example.ehhhhhh.presentation.adapters

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.R
import com.example.ehhhhhh.data.model.FireWord
import com.example.ehhhhhh.databinding.WordsItemBinding
import java.util.*

class FireWordAdapter(var words: MutableList<FireWord>, val c: Context):
    RecyclerView.Adapter<FireWordAdapter.FireWordViewHolder>(){

    private lateinit var tts: TextToSpeech
    inner class FireWordViewHolder(val binding: WordsItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FireWordViewHolder {
        val view = WordsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FireWordViewHolder(view)
    }

    override fun onBindViewHolder(holder: FireWordAdapter.FireWordViewHolder, position: Int) {
        holder.binding.wordOrig.text = words[position].orig_word
        holder.binding.wordTranslate.text = words[position].translate
        holder.binding.wordTranscription.text = words[position].transcription
        holder.binding.level.text = "Уровень изчуенности: ${words[position].level}"
        holder.binding.repDate.text = "Дата повторения: ${words[position].rep_date}"

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

        holder.itemView.setOnLongClickListener {
            if(!holder.binding.level.isVisible) {
                holder.binding.level.visibility = View.VISIBLE
                holder.binding.repDate.visibility = View.VISIBLE
            }
            else{
                holder.binding.level.visibility = View.GONE
                holder.binding.repDate.visibility = View.GONE
            }
            true
        }
    }

    override fun getItemCount(): Int = words.size

    fun setData(newList: MutableList<FireWord>){
        words = newList
        notifyDataSetChanged()
    }

    fun getWord(position: Int): FireWord {
        return words[position]
    }

}