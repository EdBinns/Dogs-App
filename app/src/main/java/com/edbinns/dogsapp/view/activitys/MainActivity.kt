package com.edbinns.dogsapp.view.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.widget.ViewPager2
import com.edbinns.dogsapp.R
import com.edbinns.dogsapp.databinding.ActivityMainBinding
import com.edbinns.dogsapp.view.adapters.FragmentAdapter
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentAdapter: FragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setNavigation()
    }


    private fun setNavigation(){
        fragmentAdapter = FragmentAdapter(supportFragmentManager,lifecycle)

        with(binding){
            viewPager.adapter = fragmentAdapter

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewPager.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            })
        }

    }

//    private fun configNav() {
//        NavigationUI.setupWithNavController(binding.bnvMenu, Navigation.findNavController(this, R.id.fragContent))
////
////        val navController = findNavController(R.id.fragContent)
////        binding.bnvMenu.setupWithNavController(navController)
//    }
}