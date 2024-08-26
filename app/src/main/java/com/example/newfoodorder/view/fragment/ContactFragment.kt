package com.example.newfoodorder.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newfoodorder.R
import com.example.newfoodorder.databinding.FragmentContactBinding
import com.example.newfoodorder.view.BaseFragment
import com.example.newfoodorder.view.activity.MainActivity
import com.example.newfoodorder.viewmodel.ContactViewModel

class ContactFragment : BaseFragment() {
    private lateinit var contactViewModel: ContactViewModel
    override fun initToolbar() {
        (activity as MainActivity).setToolBar(true, getString(R.string.contact))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentContactBinding.inflate(inflater, container, false)
        contactViewModel = ContactViewModel(requireActivity())
        binding.contactViewModel = contactViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


}