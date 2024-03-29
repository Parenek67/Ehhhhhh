package com.example.ehhhhhh.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ehhhhhh.R
import com.example.ehhhhhh.data.model.Training
import com.example.ehhhhhh.databinding.FragmentSelectDictBinding
import com.example.ehhhhhh.databinding.FragmentTrainResultBinding
import com.example.ehhhhhh.presentation.adapters.ErrorsAdapter
import com.example.ehhhhhh.presentation.viewmodel.TrainViewModelFactory
import com.example.ehhhhhh.presentation.viewmodel.TrainingViewModel
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModel
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TrainResultFragment : Fragment() {

    private var _binding: FragmentTrainResultBinding? = null
    private val binding get() = _binding!!
    var trueAnswers = 0
    var falseAnswers = 0
    private lateinit var wordsViewModel: WordsViewModel
    private lateinit var trainViewModel: TrainingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainResultBinding.inflate(inflater, container, false)
        val view = binding.root

        wordsViewModel = ViewModelProvider(this, WordsViewModelFactory(requireContext(), ""))
            .get(WordsViewModel::class.java)
        trainViewModel = ViewModelProvider(this, TrainViewModelFactory(requireContext()))
            .get(TrainingViewModel::class.java)

        val err = requireArguments().getString("err")!!.split(";").toMutableList()
        binding.rv.adapter = ErrorsAdapter(err)
        binding.rv.layoutManager = LinearLayoutManager(context)
        requireArguments().getString("res")!!.split("; ").forEach{
            Log.d("maptest", it)
            val task = it.split(" ")

            if(task[2] == "1"){
                trueAnswers++
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO){
                    wordsViewModel.plusLevel(task[0],task[1])
                }
            } else {
                falseAnswers++
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO){
                    wordsViewModel.minusLevel(task[0],task[1])
                }
            }

            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO){
                wordsViewModel.changeRepeatDate(task[0],task[1],
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                        //"2023-04-24")
            }
        }

        val train = Training(
            0,
            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
            requireArguments().getString("train")!!,
            trueAnswers,
            falseAnswers
        )

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO){
            trainViewModel.insertTrain(train)
        }

        binding.resultTrueCount.text = trueAnswers.toString()
        binding.resultFalseCount.text = falseAnswers.toString()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}