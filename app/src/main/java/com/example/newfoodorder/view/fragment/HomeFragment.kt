package com.example.newfoodorder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newfoodorder.R
import com.example.newfoodorder.databinding.FragmentHomeBinding
import com.example.newfoodorder.view.BaseFragment
import com.example.newfoodorder.view.activity.MainActivity
import com.example.newfoodorder.viewmodel.HomeViewModel

class HomeFragment : BaseFragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel = HomeViewModel(requireActivity())
        fragmentHomeBinding.homeViewModel = homeViewModel
        fragmentHomeBinding.lifecycleOwner = viewLifecycleOwner

        return fragmentHomeBinding.root
    }
    override fun initToolbar() {
        (activity as? MainActivity)?.setToolBar(true, getString(R.string.home))
    }
    override fun onDestroy() {
        super.onDestroy()
        homeViewModel.release()
    }
}