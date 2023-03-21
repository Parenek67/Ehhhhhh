package com.example.ehhhhhh.presentation.fragments

import android.content.DialogInterface
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.R
import com.example.ehhhhhh.data.model.Dictionary
import com.example.ehhhhhh.databinding.FragmentDictionariesBinding
import com.example.ehhhhhh.presentation.adapters.DictsAdapter
import com.example.ehhhhhh.presentation.viewmodel.DictViewModelFactory
import com.example.ehhhhhh.presentation.viewmodel.DictionaryViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DictsFragment : Fragment() {

    private var _binding: FragmentDictionariesBinding? = null
    private val binding get() = _binding!!
    val dicts = mutableListOf<Dictionary>()
    private lateinit var dictViewModel: DictionaryViewModel
    private lateinit var adapter: DictsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDictionariesBinding.inflate(inflater, container, false)
        val view = binding.root
        adapter = DictsAdapter(dicts, requireContext())
        binding.recyclerDictionaries.adapter=adapter
        binding.recyclerDictionaries.layoutManager = LinearLayoutManager(context)
        dictViewModel = ViewModelProvider(this, DictViewModelFactory(requireContext()))
            .get(DictionaryViewModel::class.java)

        dictViewModel.getAllDicts().observe(viewLifecycleOwner) {
            adapter.setData(it)
            if(adapter.itemCount == 0)
                binding.thumbleweed.visibility = View.VISIBLE
        }

        binding.dictsFab.setOnClickListener {
            addDictDialog()
        }
        setItemTouchHelper()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addDictDialog(){
        val dialogBuilder = context?.let { AlertDialog.Builder(it) }
        val view = layoutInflater.inflate(R.layout.add_dict_dialog, null)
        dialogBuilder!!.setView(view)

        val name = view.findViewById<EditText>(R.id.addDD_name)
        val desc = view.findViewById<EditText>(R.id.addDD_desc)

        dialogBuilder.setPositiveButton("OK", DialogInterface.OnClickListener{ _, _ ->
            Log.d("${name.text}", "aaaa")
            val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy, HH:mm"))
            val d = Dictionary("${name.text}", 0, "${desc.text}", date, false)
            dictViewModel.insertDict(d)
        })
        dialogBuilder.setNegativeButton("Отмена", DialogInterface.OnClickListener{ dialog, _ ->
            dialog.cancel()
        })

        dialogBuilder.create().show()
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
                dictViewModel.deleteDict(adapter.getDict(viewHolder.adapterPosition))
                Log.d("${viewHolder.adapterPosition}", "aaa")
            }

        }).attachToRecyclerView(binding.recyclerDictionaries)
    }
}