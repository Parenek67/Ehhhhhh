package com.example.ehhhhhh.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ehhhhhh.data.model.FireDictionary
import com.example.ehhhhhh.databinding.FragmentFirebaseDictsBinding
import com.example.ehhhhhh.presentation.adapters.FireDictsAdapter
import com.google.firebase.database.*

class FirebaseDicts : Fragment() {

    private var _binding: FragmentFirebaseDictsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FireDictsAdapter
    private lateinit var dbref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirebaseDictsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.rv.layoutManager = LinearLayoutManager(context)
        adapter = FireDictsAdapter(mutableListOf())
        binding.rv.adapter = adapter
        val dicts = mutableListOf<FireDictionary>()

        dbref = FirebaseDatabase.getInstance().getReference("Dictionary")
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(snap in snapshot.children){
                        dicts.add(snap.getValue(FireDictionary::class.java)!!)
                    }
                    adapter.setData(dicts)
                }
                //dicts.clear()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}