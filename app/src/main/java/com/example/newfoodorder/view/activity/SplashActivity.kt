package com.example.newfoodorder.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.newfoodorder.constant.GlobalFunction.startActivity
import com.example.newfoodorder.databinding.ActivitySplashBinding
import com.example.newfoodorder.viewmodel.SplashViewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    companion object {
        const val SPLASH_TIME_OUT: Long = 3000
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        binding.setSplashViewModel(SplashViewModel())
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(binding.root)

        startMainActivity()

    }

    private fun startMainActivity() {
        val handler = android.os.Handler()
        handler.postDelayed({
            startActivity(
                this@SplashActivity,
                MainActivity::class.java
            )
            finish()
        }, SPLASH_TIME_OUT)

    }
}