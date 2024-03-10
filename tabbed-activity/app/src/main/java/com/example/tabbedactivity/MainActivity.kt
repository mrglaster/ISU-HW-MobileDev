package com.example.tabbedactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        val cityNames = resources.getStringArray(R.array.cities_names)
        val cityDescriptions = resources.getStringArray(R.array.cities_descriptions)
        val cityImages = resources.getStringArray(R.array.cities_images)
        val adapter = ViewPagerAdapter(this, cityNames, cityDescriptions, cityImages)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = cityNames[position]
        }.attach()
    }
}