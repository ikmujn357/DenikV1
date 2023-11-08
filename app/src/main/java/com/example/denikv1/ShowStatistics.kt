package com.example.denikv1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShowStatistics : AppCompatActivity() {
    private val dailyStatistics = DailyStatistics()
    private val allStatistics = AllStatistics()
    private var activeFragment: Fragment = dailyStatistics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statistika)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        supportFragmentManager.beginTransaction().add(R.id.content_container, allStatistics, "2").hide(allStatistics).commit()
        supportFragmentManager.beginTransaction().add(R.id.content_container, dailyStatistics, "1").commit()

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.dailyStatitics -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(dailyStatistics).commit()
                    activeFragment = dailyStatistics
                }
                R.id.allStatitics -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(allStatistics).commit()
                    activeFragment = allStatistics
                }
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}