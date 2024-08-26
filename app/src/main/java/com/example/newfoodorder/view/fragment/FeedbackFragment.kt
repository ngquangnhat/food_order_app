package com.example.newfoodorder.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newfoodorder.R
import com.example.newfoodorder.databinding.FragmentFeedbackBinding
import com.example.newfoodorder.model.Feedback
import com.example.newfoodorder.view.BaseFragment
import com.example.newfoodorder.view.activity.MainActivity

class FeedbackFragment : BaseFragment() {

    private lateinit var feedbackViewModel: Feedback
    override fun initToolbar() {
        (activity as? MainActivity)?.setToolBar(true, getString(R.string.feedback))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val feedbackBinding = FragmentFeedbackBinding.inflate(inflater, container, false)
        feedbackViewModel = Feedback()
        feedbackBinding.feedbackModel = feedbackViewModel
        feedbackBinding.lifecycleOwner = viewLifecycleOwner
        return  feedbackBinding.root
    }

}