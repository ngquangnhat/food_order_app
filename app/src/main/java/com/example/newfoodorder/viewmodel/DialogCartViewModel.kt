package com.example.newfoodorder.viewmodel

import android.app.Activity
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.example.newfoodorder.constant.Constant
import com.example.newfoodorder.database.FoodDatabase
import com.example.newfoodorder.listener.IAddToCartSuccessListener
import com.example.newfoodorder.model.Food
import com.example.newfoodorder.utils.GlideUtils
import com.google.android.material.bottomsheet.BottomSheetDialog

class DialogCartViewModel(
    private val activity: Activity,
    private val bottomSheetDialog: BottomSheetDialog,
    val food: Food,
    private val addToCartSuccessListener: IAddToCartSuccessListener
) {
    val strTotalPrice = ObservableField<String>()
    init {
        initData()
    }
    private fun initData() {
        val totalPrice = food.getRealPrice()
        strTotalPrice.set("$totalPrice${Constant.CURRENCY}")
        food.count = 1
        food.totalPrice = totalPrice
    }
    fun onClickSubtractCount(textView: TextView){
        val count = textView.text.toString().toInt()
        if(count<= 1) return
        val newCount = count -1
        textView.text = newCount.toString()

        val totalPrice = food.getRealPrice() * newCount
        strTotalPrice.set("$totalPrice${Constant.CURRENCY}")
    }
    fun onClickAddCount(tvCount: TextView) {
        val newCount = tvCount.text.toString().toInt() + 1
        tvCount.text = newCount.toString()

        val totalPrice = food.getRealPrice() * newCount
        strTotalPrice.set("$totalPrice${Constant.CURRENCY}")

        food.count = newCount
        food.totalPrice = totalPrice
    }
    fun onClickCancel() {
        bottomSheetDialog.dismiss()
    }

    fun onClickAddToCart() {
        FoodDatabase.getInstance(activity).foodDAO().insertFood(food)
        addToCartSuccessListener.addToCartSuccess()
    }
    companion object {
        @BindingAdapter("url_image")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, url: String?) {
            GlideUtils.loadUrl(url, imageView)
        }
    }

}