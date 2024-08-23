package com.example.newfoodorder.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newfoodorder.view.fragment.CartFragment
import com.example.newfoodorder.view.fragment.ContactFragment
import com.example.newfoodorder.view.fragment.FeedbackFragment
import com.example.newfoodorder.view.fragment.HomeFragment
import com.example.newfoodorder.view.fragment.OrderFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> HomeFragment()
        1 -> CartFragment()
        2 -> FeedbackFragment()
        3 -> ContactFragment()
        4 -> OrderFragment()
        else -> HomeFragment()
    }
}