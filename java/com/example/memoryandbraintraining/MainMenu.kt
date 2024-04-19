package com.example.memoryandbraintraining


import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainMenu : AppCompatActivity() {
    private lateinit var contentFrame: FrameLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        contentFrame = findViewById<FrameLayout>(R.id.contentFrame)
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_trainings -> {
                    // Загрузить фрагмент с содержимым тренировок
                    loadFragment(fragment_daily_activity())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menu_statistics -> {
                    // Загрузить фрагмент с содержимым статистики
                    loadFragment(fragment_exercises())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menu_profile -> {
                    // Загрузить фрагмент с содержимым профиля
                    loadFragment(fragment_profile())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }


        // По умолчанию загружаем фрагмент с содержимым тренировок
        loadFragment(fragment_daily_activity())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentFrame, fragment)
            .commit()
    }
}