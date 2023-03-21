package com.example.ehhhhhh.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ehhhhhh.data.model.Word
import com.example.ehhhhhh.databinding.FragmentWordsBinding
import com.example.ehhhhhh.presentation.adapters.WordsAdapter
import com.example.ehhhhhh.presentation.viewmodel.DictViewModelFactory
import com.example.ehhhhhh.presentation.viewmodel.DictionaryViewModel
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModel
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModelFactory


class WordsFragment : Fragment(){

    private var _binding: FragmentWordsBinding? = null
    private val binding get() = _binding!!
    private lateinit var wordsViewModel: WordsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordsBinding.inflate(inflater, container, false)
        val view = binding.root
        val dictName = requireArguments().getString("name").toString()
        Log.d("namee", dictName)
        (activity as AppCompatActivity).supportActionBar!!.title=dictName

        val words = mutableListOf(Word(dictName,"Собака", "Dog", "[dog]", 0),
            Word(dictName,"Кот", "Cat", "[сat]", 0),
            Word(dictName,"Город", "Town", "[town]", 0))
        val rvWords = binding.recyclerWords
        val adapter = WordsAdapter(words)
        rvWords.adapter = adapter
        rvWords.layoutManager = LinearLayoutManager(context)

        wordsViewModel = ViewModelProvider(this, WordsViewModelFactory(requireContext(), dictName))
            .get(WordsViewModel::class.java)
        /*wordsViewModel.insertWord(words[0])
        wordsViewModel.insertWord(words[1])
        wordsViewModel.insertWord(words[2])*/

        wordsViewModel.getWordsFromDict().observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}