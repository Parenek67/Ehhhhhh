package com.example.ehhhhhh.presentation.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ehhhhhh.databinding.FragmentWordsBinding


class WordsFragment : Fragment(){

    private var _binding: FragmentWordsBinding? = null
    private val binding get() = _binding!!
    private lateinit var dict_name: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordsBinding.inflate(inflater, container, false)
        val view = binding.root
        dict_name = requireArguments().getString("tag").toString()
        (activity as AppCompatActivity).supportActionBar!!.title=dict_name
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}