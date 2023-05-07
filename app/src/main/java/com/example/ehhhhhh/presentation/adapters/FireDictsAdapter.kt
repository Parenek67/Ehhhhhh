package com.example.ehhhhhh.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.data.model.FireDictionary
import com.example.ehhhhhh.databinding.FireItemBinding

class FireDictsAdapter(var dicts: MutableList<FireDictionary>):
    RecyclerView.Adapter<FireDictsAdapter.FireDictsViewHolder>() {

    inner class FireDictsViewHolder(val binding: FireItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FireDictsViewHolder {
        val view = FireItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FireDictsViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FireDictsViewHolder, position: Int) {
        holder.binding.dictsItemName.text = dicts[position].name
        holder.binding.dictsItemWords.text= "${dicts[position].words} слов"
        holder.itemView.setOnLongClickListener {
            if(!holder.binding.dictsItemDesc.isVisible) {
                holder.binding.dictsItemDesc.visibility = View.VISIBLE
            }
            else{
                holder.binding.dictsItemDesc.visibility = View.GONE
            }
            true
        }
    }

    override fun getItemCount() = dicts.size

    fun setData(newList: MutableList<FireDictionary>){
        dicts = newList
        notifyDataSetChanged()
    }
}