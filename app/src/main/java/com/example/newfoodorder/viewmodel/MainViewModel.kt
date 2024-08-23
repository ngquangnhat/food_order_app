package com.example.newfoodorder.viewmodel

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.newfoodorder.R
import com.example.newfoodorder.adapter.MainViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainViewModel {
    var isShowToolbar: ObservableBoolean = ObservableBoolean()
    var title: ObservableField<String> = ObservableField()

    companion object{
        @BindingAdapter("item_selected")
        @JvmStatic
        fun setOnNavigationItemSelectedListener(bottomNavigationView: BottomNavigationView, viewPager: ViewPager2){
            viewPager.isUserInputEnabled = false
            val mainViewPagerAdapter = MainViewPagerAdapter(viewPager.context as FragmentActivity)
            viewPager.adapter = mainViewPagerAdapter

            bottomNavigationView.setOnItemSelectedListener {item->
                when(item.itemId){
                    R.id.nav_home -> {
                        viewPager.currentItem = 0
                        true
                    }
                    R.id.nav_cart -> {
                        viewPager.currentItem = 1
                        true
                    }
                    R.id.nav_feedback -> {
                        viewPager.currentItem = 2
                        true
                    }
                    R.id.nav_contact -> {
                        viewPager.currentItem = 3
                        true
                    }
                    R.id.nav_order -> {
                        viewPager.currentItem = 4
                        true
                    }
                    else -> false
                }
            }
        }
    }

}