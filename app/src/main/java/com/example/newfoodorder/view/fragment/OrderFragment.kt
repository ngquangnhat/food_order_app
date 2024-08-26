package com.example.newfoodorder.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newfoodorder.R
import com.example.newfoodorder.databinding.FragmentOrderBinding
import com.example.newfoodorder.view.BaseFragment
import com.example.newfoodorder.view.activity.MainActivity
import com.example.newfoodorder.viewmodel.OrderViewModel

class OrderFragment : BaseFragment() {
    private lateinit var orderViewModel: OrderViewModel
    override fun initToolbar() {
        (activity as? MainActivity)?.setToolBar(true, getString(R.string.order))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOrderBinding.inflate(inflater, container, false)
        orderViewModel = OrderViewModel(requireActivity())
        binding.orderViewModel = orderViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}