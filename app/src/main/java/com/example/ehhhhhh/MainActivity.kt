package com.example.ehhhhhh

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModel
import com.example.ehhhhhh.presentation.viewmodel.WordsViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    //private lateinit var wordsViewModel: WordsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfig = AppBarConfiguration(setOf(R.id.dictionariesFragment,
            R.id.trainingFragment,
            R.id.profileFragment,
            R.id.firebaseDicts))
        setupActionBarWithNavController(navController, appBarConfig)
        bottomNav.setupWithNavController(navController)

        /*wordsViewModel = ViewModelProvider(this, WordsViewModelFactory(this, "еда"))
            .get(WordsViewModel::class.java)
        wordsViewModel.getWordsFromDict().observe(this){
            val setup = SetupDatabase(it)
            setup.createData()
        }*/
        val setup = SetupDatabase()
        setup.createData()
    }

    override fun onSupportNavigateUp(): Boolean {
        val controller = findNavController(R.id.nav_host_fragment)
        return controller.navigateUp() || super.onSupportNavigateUp()
    }
}