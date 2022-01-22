package com.edbinns.dogsapp.view.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.fragment.app.FragmentManager

import androidx.lifecycle.Lifecycle
import com.edbinns.dogsapp.view.fragments.DogsFragment
import com.edbinns.dogsapp.view.fragments.FavoriteFragment


class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment =
        when (position) {

            1 -> FavoriteFragment()
            else -> DogsFragment()
        }

    override fun getItemCount() = 2

}