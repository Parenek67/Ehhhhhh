package com.example.ehhhhhh.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.ehhhhhh.MainActivity
import com.example.ehhhhhh.R
import com.example.ehhhhhh.SignInActivity
import com.example.ehhhhhh.databinding.FragmentProfileBinding
import com.example.ehhhhhh.databinding.FragmentTrainResultBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        firebaseAuth = FirebaseAuth.getInstance()

        binding.profileLogout.setOnClickListener{
            firebaseAuth.signOut()
            checkUser()
        }

        (activity as AppCompatActivity).supportActionBar!!.hide()

        //val uid = firebaseAuth.currentUser!!.uid
        //val email = firebaseAuth.currentUser!!.email
        val date = firebaseAuth.currentUser!!.metadata!!.creationTimestamp
        Log.d("prof", date.toString())

        binding.profileUid.text = "UID: "+ firebaseAuth.currentUser!!.uid
        binding.profileLogin.text = firebaseAuth.currentUser!!.email
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun checkUser(){
        if(firebaseAuth.currentUser == null){
            val intent = Intent(requireContext(), SignInActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar!!.show()
    }
}