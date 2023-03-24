package com.example.ehhhhhh.presentation.fragments

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.R
import com.example.ehhhhhh.data.model.Word
import com.example.ehhhhhh.databinding.FragmentWordsBinding
import com.example.ehhhhhh.presentation.adapters.DictsAdapter
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
    private lateinit var adapter: WordsAdapter
    private lateinit var dictName: String
    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordsBinding.inflate(inflater, container, false)
        val view = binding.root
        dictName = requireArguments().getString("name").toString()
        (activity as AppCompatActivity).supportActionBar!!.title=dictName

        val words = mutableListOf<Word>()
        val rvWords = binding.recyclerWords

        adapter = WordsAdapter(words, requireContext())
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

        setItemTouchHelper()
        return view
    }

    private fun setItemTouchHelper(){
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                wordsViewModel.deleteWord(adapter.getWord(viewHolder.adapterPosition))
                wordsViewModel.changeCount(dictName, -1)
                Log.d("${viewHolder.adapterPosition}", "aaa")
            }

        }).attachToRecyclerView(binding.recyclerWords)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}