package com.example.tabbedactivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val citiesNames: Array<String>,
    private val citiesDescriptions: Array<String>,
    private val citiesImages: Array<String>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return citiesNames.size
    }

    override fun createFragment(position: Int): Fragment {
        return CityFragment.newInstance(
            citiesNames[position], citiesDescriptions[position],
            citiesImages[position]
        )
    }
}