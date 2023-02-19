package com.example.ehhhhhh

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.databinding.FragmentDictionariesBinding

class DictsFragment : Fragment() {

    private var _binding: FragmentDictionariesBinding? = null
    private val binding get() = _binding!!
    val dicts = mutableListOf(
        Dictionary("Животные",44, true),
        Dictionary("Растения",15, false),
        Dictionary("IT",50, false),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDictionariesBinding.inflate(inflater, container, false)
        val view = binding.root
        val adapter = DictsAdapter(dicts)
        binding.recyclerDictionaries.adapter=adapter
        binding.recyclerDictionaries.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}