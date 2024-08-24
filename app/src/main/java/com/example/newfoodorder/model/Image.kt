package com.example.newfoodorder.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.newfoodorder.utils.GlideUtils
import java.io.Serializable

data class Image(
    var url: String? = null
) : Serializable {

    companion object {
        @JvmStatic
        @BindingAdapter("url_image")
        fun loadImageFromUrl(imageView: ImageView, url: String?) {
            url?.let {
                GlideUtils.loadUrl(it, imageView)
            }
        }
    }
}