package com.example.newfoodorder.viewmodel

import androidx.databinding.ObservableField
import com.example.newfoodorder.constant.AboutUsConfig

class SplashViewModel {
    var aboutUsTitle: ObservableField<String> = ObservableField()
    var aboutUsSlogan: ObservableField<String> = ObservableField()

    init {
        aboutUsTitle.set(AboutUsConfig.ABOUT_US_TITLE)
        aboutUsSlogan.set(AboutUsConfig.ABOUT_US_SLOGAN)
    }
}