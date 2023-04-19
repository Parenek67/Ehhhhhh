package com.example.ehhhhhh.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ehhhhhh.R
import com.example.ehhhhhh.databinding.FragmentSelectDictBinding
import com.example.ehhhhhh.databinding.FragmentWordsBinding
import com.example.ehhhhhh.presentation.adapters.SelectDictAdapter
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModel
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModelFactory


class SelectDictFragment : Fragment() {

    private var _binding: FragmentSelectDictBinding? = null
    private val binding get() = _binding!!
    private lateinit var wordsViewModel: WordsViewModel
    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectDictBinding.inflate(inflater, container, false)
        val view = binding.root

        val rv = binding.selsectdictRv
        val adapter = SelectDictAdapter(requireContext(), mutableListOf())
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)

        wordsViewModel = ViewModelProvider(this, WordsViewModelFactory(requireContext(), "None"))
            .get(WordsViewModel::class.java)

        wordsViewModel.getDictNames().observe(viewLifecycleOwner){
            adapter.setData(it)
        }

        binding.selectdictFab.setOnClickListener{
            var res = ""
            adapter.getDictNames().forEach{
                res+="$it, "
            }
            res = res.dropLast(2)
            Log.d("sdicts", res)
            bundle.putString("names", res)
            findNavController().navigate(R.id.action_selectDictFragment_to_trainingFragment, bundle)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}