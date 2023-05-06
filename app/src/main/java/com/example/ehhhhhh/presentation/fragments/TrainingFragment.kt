package com.example.ehhhhhh.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ehhhhhh.R
import com.example.ehhhhhh.data.model.TypeOfTrain
import com.example.ehhhhhh.databinding.FragmentDictionariesBinding
import com.example.ehhhhhh.databinding.FragmentTrainingBinding
import com.example.ehhhhhh.presentation.adapters.TrainListAdapter

class TrainingFragment : Fragment() {

    private var _binding: FragmentTrainingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.trainSelectedDict.text = arguments?.getString("names") ?: "Ничего не выбрано"

        val adapter = TrainListAdapter(
            mutableListOf(
                TypeOfTrain("Выбор перевода",
                    "Выберите правильный перевод слова из нескольких вариантов ответа"),
                TypeOfTrain("Перевод слова",
                    "Переведите слово самостоятельно, без вариантов ответа")
            ),
            arguments?.getString("names") ?: "Ничего не выбрано")

        Log.d("maptest", binding.trainSelectedDict.text.toString())
        binding.trainRv.adapter = adapter
        binding.trainRv.layoutManager = LinearLayoutManager(context)

        binding.selectDicts.setOnClickListener{
            findNavController().navigate(R.id.action_trainingFragment_to_selectDictFragment)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}