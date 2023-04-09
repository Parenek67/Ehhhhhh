package com.example.ehhhhhh.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.ehhhhhh.R
import com.example.ehhhhhh.databinding.FragmentDictionariesBinding
import com.example.ehhhhhh.databinding.FragmentFindTrainBinding
import com.example.ehhhhhh.databinding.FragmentTrainingBinding

class FindTrainFragment : Fragment() {

    private var _binding: FragmentFindTrainBinding? = null
    private val binding get() = _binding!!
    val bundle = Bundle()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindTrainBinding.inflate(inflater, container, false)
        val view = binding.root
        val trainType = requireArguments().getString("train").toString()
        (activity as AppCompatActivity).supportActionBar!!.title=trainType

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}