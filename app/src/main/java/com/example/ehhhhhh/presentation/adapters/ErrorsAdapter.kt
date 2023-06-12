package com.example.ehhhhhh.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.databinding.ErrorsItemBinding
import com.example.ehhhhhh.databinding.TrainItemBinding

class ErrorsAdapter(val errors: MutableList<String>): RecyclerView.Adapter<ErrorsAdapter.ErrorsViewHolder>() {

    inner class ErrorsViewHolder(val binding: ErrorsItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ErrorsViewHolder {
        val view = ErrorsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ErrorsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ErrorsViewHolder, position: Int) {

        holder.binding.rus.text = errors[position].split(" ")[0]
        holder.binding.error.text = errors[position].split(" ")[1]
        holder.binding.correct.text = errors[position].split(" ")[2]
    }

    override fun getItemCount() = errors.size
}