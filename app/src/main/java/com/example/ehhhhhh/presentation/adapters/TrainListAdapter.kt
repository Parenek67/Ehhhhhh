package com.example.ehhhhhh.presentation.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.R
import com.example.ehhhhhh.data.model.TypeOfTrain
import com.example.ehhhhhh.databinding.TrainItemBinding

class TrainListAdapter(val trainList: MutableList<TypeOfTrain>, val dictName: String):
    RecyclerView.Adapter<TrainListAdapter.TrainListViewHolder>() {

    val bundle = Bundle()

    inner class TrainListViewHolder(val binding: TrainItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainListViewHolder {
        val view = TrainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrainListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrainListViewHolder, position: Int) {
        holder.binding.name.text = trainList[position].name
        holder.binding.desc.text = trainList[position].desc

        holder.binding.eng.setOnClickListener{

            bundle.putString("train", trainList[position].name)
            bundle.putString("dictName", dictName)
            bundle.putString("lang", "english")

            if(holder.binding.name.text.equals("Выбор перевода"))
                Navigation.findNavController(it).navigate(R.id.action_trainingFragment_to_findTrainFragment, bundle)
        }

        holder.binding.rus.setOnClickListener{

            bundle.putString("train", trainList[position].name)
            bundle.putString("dictName", dictName)
            bundle.putString("lang", "russian")

            if(holder.binding.name.text.equals("Выбор перевода"))
                Navigation.findNavController(it).navigate(R.id.action_trainingFragment_to_findTrainFragment, bundle)
        }
    }

    override fun getItemCount() = trainList.size
}