package com.example.ehhhhhh.presentation.fragments

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ehhhhhh.R
import com.example.ehhhhhh.data.model.Word
import com.example.ehhhhhh.databinding.FragmentWordsBinding
import com.example.ehhhhhh.presentation.adapters.WordsAdapter
import com.example.ehhhhhh.presentation.viewmodel.DictViewModelFactory
import com.example.ehhhhhh.presentation.viewmodel.DictionaryViewModel
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModel
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModelFactory
import java.util.*


class WordsFragment : Fragment(){

    private var _binding: FragmentWordsBinding? = null
    private val binding get() = _binding!!
    private lateinit var wordsViewModel: WordsViewModel
    private lateinit var tts: TextToSpeech
    val bundle = Bundle()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordsBinding.inflate(inflater, container, false)
        val view = binding.root
        val dictName = requireArguments().getString("name").toString()
        (activity as AppCompatActivity).supportActionBar!!.title=dictName

        val words = mutableListOf<Word>()
        val rvWords = binding.recyclerWords

        val adapter = WordsAdapter(words, requireContext())
        rvWords.adapter = adapter
        rvWords.layoutManager = LinearLayoutManager(context)

        wordsViewModel = ViewModelProvider(this, WordsViewModelFactory(requireContext(), dictName))
            .get(WordsViewModel::class.java)

        wordsViewModel.getWordsFromDict().observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        binding.wordsFab.setOnClickListener{
            bundle.putString("name", dictName)
            Navigation.findNavController(it).navigate(R.id.action_dictionaryFragment_to_addWordFragment, bundle)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}