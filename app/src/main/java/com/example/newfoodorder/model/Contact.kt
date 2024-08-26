package com.example.newfoodorder.model

import android.app.Activity
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.newfoodorder.constant.GlobalFunction

class Contact(
    var id: Int,
    var image: Int,
    var name: String
) {
    companion object{
        const val FACEBOOK :Int = 0
        const val HOTLINE :Int = 1
        const val GMAIL :Int = 2
        const val SKYPE :Int = 3
        const val YOUTUBE :Int = 4
        const val ZALO :Int = 5

        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageViewResource(imageView: ImageView, resource: Int) {
            imageView.setImageResource(resource)
        }
    }
    fun clickContactItem(view : View){
        val context = view.context
        when(id){
            FACEBOOK -> GlobalFunction.onClickOpenFacebook(context)
            HOTLINE -> GlobalFunction.callPhoneNumber(context as Activity)
            GMAIL -> GlobalFunction.onClickOpenGmail(context)
            SKYPE -> GlobalFunction.onClickOpenSkype(context)
            YOUTUBE -> GlobalFunction.onClickOpenYoutubeChannel(context)
            ZALO -> GlobalFunction.onClickOpenZalo(context)

        }
    }

}