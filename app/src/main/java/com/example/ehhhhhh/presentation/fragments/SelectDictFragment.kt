package com.example.ehhhhhh.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ehhhhhh.R
import com.example.ehhhhhh.databinding.FragmentSelectDictBinding
import com.example.ehhhhhh.databinding.FragmentWordsBinding
import com.example.ehhhhhh.presentation.adapters.SelectDictAdapter


class SelectDictFragment : Fragment() {

    private var _binding: FragmentSelectDictBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectDictBinding.inflate(inflater, container, false)
        val view = binding.root

        val rv = binding.selsectdictRv
        val adapter = SelectDictAdapter(mutableListOf())
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}