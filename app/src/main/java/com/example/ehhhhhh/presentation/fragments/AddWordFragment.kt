package com.example.ehhhhhh.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ehhhhhh.R
import com.example.ehhhhhh.data.model.Word
import com.example.ehhhhhh.data.repository.YDictRepository
import com.example.ehhhhhh.databinding.FragmentAddWordBinding
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModel
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModelFactory
import com.example.ehhhhhh.presentation.viewmodel.YDictViewModel
import com.example.ehhhhhh.presentation.viewmodel.YDictViewModelFactory
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddWordFragment : Fragment() {

    private var _binding: FragmentAddWordBinding? = null
    private val binding get() = _binding!!
    private lateinit var wordsViewModel: WordsViewModel
    val bundle = Bundle()
    //////
    private lateinit var translator: Translator
    private var bool = false
    val options = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.RUSSIAN)
        .setTargetLanguage(TranslateLanguage.ENGLISH)
        .build()
    var conditions = DownloadConditions.Builder()
        .requireWifi()
        .build()
    private lateinit var viewModel: YDictViewModel
    val repository = YDictRepository()
    private val key = "dict.1.1.20230331T191958Z.4928d8dc763a4001.141ac10c39fef65bc7b8d3509022e2e7eaa148e0"
    /////
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddWordBinding.inflate(inflater, container, false)
        val view = binding.root
        translator = Translation.getClient(options)
        val dictName = requireArguments().getString("name").toString()
        //binding.addWordDict.text = dictName
        (activity as AppCompatActivity).supportActionBar!!.title=dictName
        wordsViewModel = ViewModelProvider(this, WordsViewModelFactory(requireContext(), dictName))
            .get(WordsViewModel::class.java)
        viewModel = ViewModelProvider(this, YDictViewModelFactory(repository))
            .get(YDictViewModel::class.java)

        binding.addWordFab.setOnClickListener{
            val word = Word(dictName,
            binding.addWordOrig.text.toString(),
            binding.addWordTranslate.text.toString(),
            binding.addWordTranscription.text.toString(), 0,
            //LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
            )
            wordsViewModel.insertWord(word)
            wordsViewModel.changeCount(dictName, 1)
            bundle.putString("name", dictName)
            findNavController().navigate(R.id.action_addWordFragment_to_dictionaryFragment, bundle)
        }

        binding.addWordTranslateBtn.setOnClickListener{
            translate()
        }

        binding.autocomplete.setOnCheckedChangeListener{ buttonView, isChecked ->
            if (isChecked){
                binding.addWordTranslate.focusable = View.NOT_FOCUSABLE
                binding.addWordTranscription.focusable = View.NOT_FOCUSABLE
                binding.addWordAlternative.focusable = View.NOT_FOCUSABLE
                binding.addWordSynonim.focusable = View.NOT_FOCUSABLE
            }else{
                binding.addWordTranslate.isFocusable = true
                binding.addWordTranslate.isFocusableInTouchMode = true
                binding.addWordTranscription.isFocusable = true
                binding.addWordTranscription.isFocusableInTouchMode = true
                binding.addWordAlternative.isFocusable = true
                binding.addWordAlternative.isFocusableInTouchMode = true
                binding.addWordSynonim.isFocusable = true
                binding.addWordSynonim.isFocusableInTouchMode = true
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun translate(){
        translator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                bool = true
                translator.translate(binding.addWordOrig.text.toString())
                    .addOnSuccessListener { translatedText ->
                        Log.d("dictt", translatedText)
                        if(translatedText.contains("the ")) {
                            binding.addWordTranslate.setText(translatedText.split(" ")[1])
                            ydict(translatedText.split(" ")[1])
                        }
                        else {
                            binding.addWordTranslate.setText(translatedText)
                            ydict(translatedText)
                        }
                    }
                    .addOnFailureListener { exception ->
                        binding.addWordTranslate.setText(exception.message)
                    }
            }
            .addOnFailureListener { exception ->
                bool = false
            }
    }

    private fun ydict(translate: String){
        viewModel.getWord(key, "en-en", translate)
        viewModel.response.observe(viewLifecycleOwner) {
            if(it.isSuccessful) {
                Log.d("dictt", "ок")
                //Log.d("dictt", "слово ${it.body()!!.def[0].text}")
                //Log.d("dictt", "транскрипция ${it.body()!!.def[0].ts}")
                Log.d("dictt", "другой перевод ${it.body()?.def?.get(0)?.tr?.get(0)?.text ?: "null"}")
                Log.d("dictt", "синоним ${it.body()?.def?.get(0)?.tr?.get(0)?.syn?.get(0)?.text ?: "null"}")
                //binding.addWordTranslate.setText(it.body()?.def?.get(0)?.text ?: "null")
                binding.addWordTranscription.setText(it.body()?.def?.get(0)?.ts ?: "null")
                binding.addWordAlternative.setText(it.body()?.def?.get(0)?.tr?.get(0)?.text ?: "null")
                binding.addWordSynonim.setText(it.body()?.def?.get(0)?.tr?.get(0)?.syn?.get(0)?.text ?: "null")
            }
            else{
                Log.d("dictt", "ошибка")
            }
        }
    }
}