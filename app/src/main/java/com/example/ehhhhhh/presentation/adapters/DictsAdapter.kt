package com.example.ehhhhhh.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.R
import com.example.ehhhhhh.data.model.Dictionary
import com.example.ehhhhhh.databinding.DictsItemBinding

class DictsAdapter(var dicts: MutableList<Dictionary>,
                   val context: Context):
    RecyclerView.Adapter<DictsAdapter.DictsViewHolder>() {

    val bundle = Bundle()

    inner class DictsViewHolder(val binding: DictsItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictsViewHolder {
        val view = DictsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DictsViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DictsViewHolder, position: Int) {
        holder.binding.dictsItemName.text=dicts[position].name
        //holder.binding.dictsItemWords.text= "${dicts[position].words} слов"
        if(dicts[position].name == "Еда")
            holder.binding.dictsItemWords.text= "7 слов"
        else
            holder.binding.dictsItemWords.text= "${dicts[position].words} слов"
        holder.binding.dictsItemDesc.text=dicts[position].description
        holder.binding.dictsItemDate.text="Последнее изменение: ${dicts[position].date}"
        if(dicts[position].isFavourite){
            holder.binding.dictsItemFav.setImageResource(R.drawable.favorite)
        }
        else{
            holder.binding.dictsItemFav.setImageResource(R.drawable.favorite_border)
        }
        holder.itemView.setOnLongClickListener {
            if(!holder.binding.dictsItemDesc.isVisible) {
                holder.binding.dictsItemDesc.visibility = View.VISIBLE
                holder.binding.dictsItemDate.visibility = View.VISIBLE
            }
            else{
                holder.binding.dictsItemDesc.visibility = View.GONE
                holder.binding.dictsItemDate.visibility = View.GONE
            }
            true
        }
        holder.itemView.setOnClickListener{
            bundle.putString("name", dicts[position].name)
            Log.d("namee", dicts[position].name)
            Navigation.findNavController(it).navigate(R.id.action_dictionariesFragment_to_dictionaryFragment, bundle)
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