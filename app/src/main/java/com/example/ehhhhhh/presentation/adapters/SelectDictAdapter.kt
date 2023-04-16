package com.example.ehhhhhh.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.databinding.SelectDictItemBinding

class SelectDictAdapter(val list: MutableList<String>): RecyclerView.Adapter<SelectDictAdapter.SelectDictViewHolder>() {

    inner class SelectDictViewHolder(val binding: SelectDictItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectDictViewHolder {
        val view = SelectDictItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectDictViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectDictViewHolder, position: Int) {
        holder.binding.selectDictItemName.text = list[position]
    }

    override fun getItemCount() = list.size
}