package com.example.ehhhhhh.presentation.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.R
import com.example.ehhhhhh.data.model.Dictionary
import com.example.ehhhhhh.data.model.Word
import com.example.ehhhhhh.databinding.FragmentWordsBinding
import com.example.ehhhhhh.presentation.adapters.DictsAdapter
import com.example.ehhhhhh.presentation.adapters.WordsAdapter
import com.example.ehhhhhh.presentation.viewmodel.DictViewModelFactory
import com.example.ehhhhhh.presentation.viewmodel.DictionaryViewModel
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModel
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModelFactory
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class WordsFragment : Fragment(){

    private var _binding: FragmentWordsBinding? = null
    private val binding get() = _binding!!
    private lateinit var wordsViewModel: WordsViewModel
    private lateinit var adapter: WordsAdapter
    private lateinit var dictName: String
    private var list = mutableListOf<Word>()
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
            list = it
            adapter.setData(it)
        }

        binding.wordsFab.setOnClickListener{
            bundle.putString("name", dictName)
            Navigation.findNavController(it).navigate(R.id.action_dictionaryFragment_to_addWordFragment, bundle)
        }

        setItemTouchHelper()

        binding.search.clearFocus()
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return true
            }

        })

        binding.filter.setOnClickListener{
            //adapter.setData(list.sortedBy {it.orig_word}.reversed().toMutableList())
            /*adapter.setData(list.sortedBy {
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val date = sdf.parse(it.rep_date)
                date
            }.reversed().toMutableList())*/
            filterDialog()
        }

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

    fun filter(query: String?){
        if(query != null){
            val filterList = mutableListOf<Word>()
            /*
            list.sortBy {
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val date = sdf.parse(it.rep_date)
                date
            }*/
            for(i in list){
                if(i.orig_word.toLowerCase(Locale.ROOT).contains(query)){
                    filterList.add(i)
                }
            }
            adapter.setData(filterList)
        }
    }

    fun filterDialog(){
        val dialogBuilder = context?.let { AlertDialog.Builder(it) }
        val view = layoutInflater.inflate(R.layout.filter_dialog, null)
        dialogBuilder!!.setView(view)

        val group = view.findViewById<RadioGroup>(R.id.radio_group)

        dialogBuilder.setPositiveButton("OK", DialogInterface.OnClickListener{ _, _ ->
            val radio: RadioButton = view.findViewById(group.checkedRadioButtonId)
            Log.d("filter", radio.text.toString())
            if(radio.text.toString() == "Переводу"){
                adapter.setData(list.sortedBy {it.translate.lowercase()}.toMutableList())
            }
            if(radio.text.toString() == "Дате повторения"){
                adapter.setData(list.sortedBy {
                    val sdf = SimpleDateFormat("yyyy-MM-dd")
                    val date = sdf.parse(it.rep_date)
                    date
                }.reversed().toMutableList())
            }
            if(radio.text.toString() == "Слову на русском"){
                adapter.setData(list.sortedBy {it.orig_word.lowercase()}.toMutableList())
            }
        })
        dialogBuilder.setNegativeButton("Отмена", DialogInterface.OnClickListener{ dialog, _ ->
            dialog.cancel()
        })

        dialogBuilder.create().show()
    }
}
