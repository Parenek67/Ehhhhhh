package com.example.ehhhhhh.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.R
import com.example.ehhhhhh.data.model.Word
import com.example.ehhhhhh.databinding.FragmentAddWordBinding
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModel
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModelFactory

class AddWordFragment : Fragment() {

    private var _binding: FragmentAddWordBinding? = null
    private val binding get() = _binding!!
    private lateinit var wordsViewModel: WordsViewModel
    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddWordBinding.inflate(inflater, container, false)
        val view = binding.root
        val dictName = requireArguments().getString("name").toString()
        binding.addWordDict.text = dictName
        wordsViewModel = ViewModelProvider(this, WordsViewModelFactory(requireContext(), dictName))
            .get(WordsViewModel::class.java)

        binding.addWordFab.setOnClickListener{
            val word = Word(dictName,
            binding.addWordOrig.text.toString(),
            binding.addWordTranslate.text.toString(),
            binding.addWordTranscription.text.toString(), 0)
            wordsViewModel.insertWord(word)
            wordsViewModel.changeCount(dictName, 1)
            bundle.putString("name", dictName)
            findNavController().navigate(R.id.action_addWordFragment_to_dictionaryFragment, bundle)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}