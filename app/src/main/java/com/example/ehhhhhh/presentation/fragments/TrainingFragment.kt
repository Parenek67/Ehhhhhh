package com.example.ehhhhhh.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ehhhhhh.R
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

        val adapter = TrainListAdapter(mutableListOf("Поиск перевода", "Поиск слова",
            "Написание слова по переводу", "Написание перевода по слову"), binding.trainSelectedDict.text.toString())
        Log.d("maptest", binding.trainSelectedDict.text.toString())
        binding.trainRv.adapter = adapter
        binding.trainRv.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}