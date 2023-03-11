package com.example.ehhhhhh.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.R
import com.example.ehhhhhh.data.model.Dictionary
import com.example.ehhhhhh.databinding.DictsItemBinding

class DictsAdapter(var dicts: MutableList<Dictionary>):
    RecyclerView.Adapter<DictsAdapter.DictsViewHolder>() {

    inner class DictsViewHolder(val binding: DictsItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictsViewHolder {
        val view = DictsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DictsViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DictsViewHolder, position: Int) {
        holder.binding.dictsItemName.text=dicts[position].name
        holder.binding.dictsItemWords.text=dicts[position].words.toString()+" слов"
        if(dicts[position].isFavourite){
            holder.binding.dictsItemFav.setImageResource(R.drawable.favorite)
        }
        else{
            holder.binding.dictsItemFav.setImageResource(R.drawable.favorite_border)
        }
    }

    override fun getItemCount() = dicts.size

    fun setData(newList: MutableList<Dictionary>){
        dicts = newList
        notifyDataSetChanged()
    }

    fun getDict(position: Int): Dictionary{
        return dicts[position]
    }
}