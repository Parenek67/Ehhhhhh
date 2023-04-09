package com.example.ehhhhhh.presentation.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.R
import com.example.ehhhhhh.databinding.TrainItemBinding

class TrainListAdapter(val trainList: MutableList<String>):
    RecyclerView.Adapter<TrainListAdapter.TrainListViewHolder>() {

    val bundle = Bundle()

    inner class TrainListViewHolder(val binding: TrainItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainListViewHolder {
        val view = TrainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrainListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrainListViewHolder, position: Int) {
        holder.binding.trainitemName.text = trainList[position]
        holder.binding.trainItemNext.setOnClickListener{
            bundle.putString("train", trainList[position])
            if(holder.binding.trainitemName.text.contains("Поиск"))
            Navigation.findNavController(it).navigate(R.id.action_trainingFragment_to_findTrainFragment, bundle)
        }
    }

    override fun getItemCount() = trainList.size
}