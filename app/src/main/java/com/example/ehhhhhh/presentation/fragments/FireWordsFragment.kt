package com.example.ehhhhhh.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ehhhhhh.R
import com.example.ehhhhhh.data.model.Dictionary
import com.example.ehhhhhh.data.model.FireDictionary
import com.example.ehhhhhh.data.model.FireWord
import com.example.ehhhhhh.data.model.Word
import com.example.ehhhhhh.databinding.FragmentFireWordsBinding
import com.example.ehhhhhh.presentation.adapters.FireWordAdapter
import com.example.ehhhhhh.presentation.viewmodel.DictViewModelFactory
import com.example.ehhhhhh.presentation.viewmodel.DictionaryViewModel
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModel
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModelFactory
import com.google.firebase.database.*


class FireWordsFragment : Fragment() {

    private var _binding: FragmentFireWordsBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbref: DatabaseReference
    private lateinit var adapter: FireWordAdapter
    private lateinit var dictViewModel: DictionaryViewModel
    private lateinit var wordsViewModel: WordsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFireWordsBinding.inflate(inflater, container, false)
        val view = binding.root
        val d = requireArguments().getString("dict").toString().split(";")
        val dict = Dictionary(d[0],d[1].toInt(),d[2],d[3],d[4].toBoolean())
        Log.d("eeeee", dict.toString())

        (activity as AppCompatActivity).supportActionBar!!.title=dict.name

        binding.rv.layoutManager = LinearLayoutManager(context)
        adapter = FireWordAdapter(mutableListOf(), requireContext())
        binding.rv.adapter = adapter
        val words = mutableListOf<FireWord>()

        dbref = FirebaseDatabase.getInstance().getReference(dict.name)
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(snap in snapshot.children){
                        words.add(snap.getValue(FireWord::class.java)!!)
                    }
                    adapter.setData(words)
                }
                //dicts.clear()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        binding.fab.setOnClickListener{
            dictViewModel = ViewModelProvider(this, DictViewModelFactory(requireContext()))
                .get(DictionaryViewModel::class.java)
            dictViewModel.insertDict(dict)
            Thread.sleep(5000)
            wordsViewModel = ViewModelProvider(this, WordsViewModelFactory(requireContext(), dict.name))
                .get(WordsViewModel::class.java)
            val w = mutableListOf<Word>()
            words.forEach{ e ->
                w.add(
                    Word(
                        e.dict_name!!,
                        e.orig_word!!,
                        e.translate!!,
                        e.transcription!!,
                        e.level!!,
                        e.rep_date!!
                    )
                )
                Log.d("eeeee", w.last().toString())
            }
            w.forEach{
                wordsViewModel.insertWord(it)
            }
            Navigation.findNavController(it).navigate(R.id.action_fireWordsFragment_to_dictionariesFragment)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}