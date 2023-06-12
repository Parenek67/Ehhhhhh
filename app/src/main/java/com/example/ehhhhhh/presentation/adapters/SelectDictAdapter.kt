package com.example.ehhhhhh.presentation.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.R
import com.example.ehhhhhh.databinding.SelectDictItemBinding

class SelectDictAdapter(val c: Context, var list: MutableList<String>): RecyclerView.Adapter<SelectDictAdapter.SelectDictViewHolder>() {

    val selected = mutableListOf<String>()

    inner class SelectDictViewHolder(val binding: SelectDictItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectDictViewHolder {
        val view = SelectDictItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectDictViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectDictViewHolder, position: Int) {
        holder.binding.selectDictItemName.text = list[position]
        holder.binding.selectedDictItemCl.setOnClickListener{
            Log.d("sdicts",list[position])
            val element = holder.binding.selectDictItemName.text.toString()
            if(selected.contains(element)){
                selected.remove(element)
                holder.binding.selectedDictItemCardview.setCardBackgroundColor(c.resources.getColor(R.color.white))
                Log.d("sdicts","выбрано $selected")
            }
            else{
                selected.add(element)
                holder.binding.selectedDictItemCardview.setCardBackgroundColor(c.resources.getColor(R.color.purple_200))
            }
        }
    }

    override fun getItemCount() = list.size

    fun setData(newList: MutableList<String>){
        list = newList
        notifyDataSetChanged()
    }

    fun getDictNames() = selected
}