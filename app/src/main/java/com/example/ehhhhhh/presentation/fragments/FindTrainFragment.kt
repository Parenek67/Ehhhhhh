package com.example.ehhhhhh.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.ehhhhhh.R
import com.example.ehhhhhh.data.model.Word
import com.example.ehhhhhh.databinding.FragmentFindTrainBinding
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModel
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModelFactory
import kotlinx.coroutines.*

class FindTrainFragment : Fragment() {

    private lateinit var wordsViewModel: WordsViewModel
    private var _binding: FragmentFindTrainBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: MutableMap<String, MutableList<String>>
    var count = 0
    val trueAnswers = mutableMapOf<String, String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindTrainBinding.inflate(inflater, container, false)
        val view = binding.root
        val trainType = requireArguments().getString("train").toString()
        (activity as AppCompatActivity).supportActionBar!!.title=trainType
        val dictName = requireArguments().getString("dictName").toString()
        Log.d("train", dictName)

        wordsViewModel = ViewModelProvider(this, WordsViewModelFactory(requireContext(), dictName))
            .get(WordsViewModel::class.java)

        /*wordsViewModel.getWordsFromDict().observe(viewLifecycleOwner) {
            train(it)
        }*/
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            train(wordsViewModel.getWordsForTrain())
        }

        binding.answer1.setOnClickListener{
            if (trueAnswers.get(binding.findtrainQuestion.text) != binding.answer1.text){
                Log.d("maptest", "false")
                binding.answer1.setTextColor(getResources().getColor(R.color.false_answer))
            }
            else {
                Log.d("maptest", "true")
                binding.answer1.setTextColor(getResources().getColor(R.color.true_answer))
            }
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000)
                binding.answer1.setTextColor(getResources().getColor(R.color.black))
                question()
            }
        }

        binding.answer2.setOnClickListener{
            if (trueAnswers.get(binding.findtrainQuestion.text) != binding.answer2.text){
                Log.d("maptest", "false")
                binding.answer2.setTextColor(getResources().getColor(R.color.false_answer))
            }
            else {
                Log.d("maptest", "true")
                binding.answer2.setTextColor(getResources().getColor(R.color.true_answer))
            }
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000)
                binding.answer2.setTextColor(getResources().getColor(R.color.black))
                question()
            }
        }

        binding.answer3.setOnClickListener{
            if (trueAnswers.get(binding.findtrainQuestion.text) != binding.answer3.text){
                Log.d("maptest", "false")
                binding.answer3.setTextColor(getResources().getColor(R.color.false_answer))
            }
            else {
                Log.d("maptest", "true")
                binding.answer3.setTextColor(getResources().getColor(R.color.true_answer))
            }
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000)
                binding.answer3.setTextColor(getResources().getColor(R.color.black))
                question()
            }
        }

        binding.answer4.setOnClickListener{
            if (trueAnswers.get(binding.findtrainQuestion.text) != binding.answer4.text){
                Log.d("maptest", "false")
                binding.answer4.setTextColor(getResources().getColor(R.color.false_answer))
            }
            else {
                Log.d("maptest", "true")
                binding.answer4.setTextColor(getResources().getColor(R.color.true_answer))
            }
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000)
                binding.answer4.setTextColor(getResources().getColor(R.color.black))
                question()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    ///выбрать перевод с русского на англ
    fun train(list: MutableList<Word>){
        map = mutableMapOf()//набор заданий с ответами
        val allAnswers = mutableListOf<String>()
        list.forEach{
            allAnswers.add(it.translate)
        }
        list.forEach{
            val answers = mutableListOf<String>()
            trueAnswers.put(it.orig_word, it.translate)
            //
            allAnswers.remove(it.translate)
            allAnswers.shuffled()
            //
            answers.add(it.translate)
            answers.add(allAnswers[0])
            answers.add(allAnswers[1])
            answers.add(allAnswers[2])
            allAnswers.add(it.translate)
            map.put(it.orig_word, answers)
        }
        map.forEach{
            Log.d("maptest", "${it.key} - ${it.value}")
        }
        question()
    }

    fun question(){
        binding.findtrainQuestion.text = map.keys.elementAt(count)
        val a = map.get(map.keys.elementAt(count))?.shuffled()
        binding.answer1.text = a?.get(0)
        binding.answer2.text = a?.get(1)
        binding.answer3.text = a?.get(2)
        binding.answer4.text = a?.get(3)
        count++
    }

}