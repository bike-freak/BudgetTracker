package com.example.budgettracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var navigationController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navigationHostFragment=supportFragmentManager.findFragmentById(R.id.navigation_fragment) as NavHostFragment
        navigationController = navigationHostFragment.navController
        setupWithNavController(bottomNavigationView,navigationController)

        val databaseAc = Room.databaseBuilder(this,AccountDatabase::class.java,"AccDB").build()

        GlobalScope.launch {
            if (!(databaseAc.AccountDAO().isRowExists(1))) {
                databaseAc.AccountDAO().insertAcc(Account(1, 2000))
            }
        }

    }
}