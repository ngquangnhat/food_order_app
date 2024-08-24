package com.example.newfoodorder.view.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.example.newfoodorder.R
import com.example.newfoodorder.databinding.ActivityMainBinding
import com.example.newfoodorder.view.BaseActivity
import com.example.newfoodorder.viewmodel.MainViewModel

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        mainViewModel = MainViewModel()
        binding.setMainViewModel(mainViewModel)
        binding.lifecycleOwner = this
        //enableEdgeToEdge()
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showConfirmExitApp()
            }
        })

    }

    fun setToolBar(isShow: Boolean, title: String?) {
        mainViewModel.isShowToolbar.set(isShow)
        if (isShow) {
            mainViewModel.title.set(title)
        }
    }

    private fun showConfirmExitApp() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.app_name))
            .setMessage(getString(R.string.msg_exit_app))
            .setPositiveButton(getString(R.string.action_ok)) { _, _ -> finish() }
            .setNegativeButton(getString(R.string.action_cancel)) { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .show()
    }
}