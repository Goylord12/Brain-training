package com.example.memoryandbraintraining

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var bnt_enter: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var imageView: ImageView
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        bnt_enter = findViewById(R.id.bnt_enter)
        imageView = findViewById(R.id.imageView)
        imageView.scaleX = 0.5f // Начальный масштаб по оси X
        imageView.scaleY = 0.5f // Начальный масштаб по оси Y
        imageView.animate()
            .scaleX(1f) // Конечный масштаб по оси X
            .scaleY(1f) // Конечный масштаб по оси Y
            .setDuration(1000) // Продолжительность анимации в миллисекундах
            .setInterpolator(AccelerateDecelerateInterpolator()) // Интерполятор для плавности анимации
            .start()

        bnt_enter.setOnClickListener {
            bnt_enter.visibility = View.GONE
            imageView.visibility = View.GONE
            if (currentUser != null) {
                // Пользователь уже аутентифицирован, переходим на главный экран
                startActivity(Intent(this, MainMenu::class.java))
                finish()
            }
            else{
                val viewPager: ViewPager = findViewById(R.id.viewPager)
                val pagerAdapter = AuthenticationPagerAdapter(supportFragmentManager)

                pagerAdapter.addFragment(LoginFragment())
                pagerAdapter.addFragment(RegisterFragment())
                viewPager.adapter = pagerAdapter
            }

        }




    }

    class AuthenticationPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val fragmentList = ArrayList<Fragment>()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment) {
            fragmentList.add(fragment)
        }
    }
}