package com.example.mad_assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(Home_fragment())
        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.Home -> {
                    loadFragment(Home_fragment())
                    true
                }
                R.id.Walk -> {
                    loadFragment(Walk_fragment())
                    true
                }
                R.id.Note -> {
                    loadFragment(Note_fragment())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

}

// from https://www.geeksforgeeks.org/bottom-navigation-bar-in-android-using-kotlin/