package com.example.newfoodorder.utils

import android.widget.ImageView
import com.bumptech.glide.Glide;
import com.example.newfoodorder.R

object GlideUtils {
    fun loadUrlBanner(url: String?, imageView: ImageView) {
        if (StringUtil.isEmpty(url)) {
            imageView.setImageResource(R.drawable.img_no_image)
            return
        }
        Glide.with(imageView.context)
            .load(url)
            .error(R.drawable.img_no_image)
            .dontAnimate()
            .into(imageView)
    }

    fun loadUrl(url: String?, imageView: ImageView) {
        if (StringUtil.isEmpty(url)) {
            imageView.setImageResource(R.drawable.img_no_image)
            return
        }
        Glide.with(imageView.context)
            .load(url)
            .error(R.drawable.img_no_image)
            .dontAnimate()
            .into(imageView)
    }
}