package com.example.newfoodorder.viewmodel

import android.app.Activity
import android.graphics.Paint
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newfoodorder.R
import com.example.newfoodorder.adapter.MoreImageAdapter
import com.example.newfoodorder.constant.Constant
import com.example.newfoodorder.database.FoodDatabase
import com.example.newfoodorder.databinding.LayoutBottomSheetCartBinding
import com.example.newfoodorder.event.ReloadListCartEvent
import com.example.newfoodorder.listener.IAddToCartSuccessListener
import com.example.newfoodorder.model.Food
import com.example.newfoodorder.model.Image
import com.example.newfoodorder.utils.GlideUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.greenrobot.eventbus.EventBus

class FoodDetailViewModel(
    private val activity: Activity,
    val food: Food
) {
    val isSale = ObservableBoolean()
    val isMoreImages = ObservableBoolean()
    val isFoodInCart = ObservableBoolean()
    val listMoreImages = ObservableArrayList<Image>()
    val strStatusCart = ObservableField<String>()

    var strSale: String? = null
    var strPriceOld: String? = null
    var strRealPrice: String? = null

    init {
        initData()
    }

    private fun initData() {
        if (food.sale <= 0) {
            isSale.set(false)
            strRealPrice = "${food.price}${Constant.CURRENCY}"
        } else {
            isSale.set(true)
            strSale = "${activity.getString(R.string.label_discount)} ${food.sale}%"
            strPriceOld = "${food.price}${Constant.CURRENCY}"
            strRealPrice = "${food.getRealPrice()}${Constant.CURRENCY}"
        }
        if (food.images.isNullOrEmpty()) {
            isMoreImages.set(false)
        } else {
            isMoreImages.set(true)
            listMoreImages.addAll(food.images!!)
        }
        val foodExistsInCart = isFoodInCart(food.id)
        isFoodInCart.set(foodExistsInCart)
        strStatusCart.set(if (foodExistsInCart) activity.getString(R.string.action_added_cart) else activity.getString(R.string.action_add_cart))
    }

    fun getStrStatusCart(textView: TextView): ObservableField<String> {
        if (isFoodInCart.get()) {
            textView.setBackgroundResource(R.drawable.bg_gray_shape_corner_6)
            textView.setTextColor(ContextCompat.getColor(activity, R.color.textColorPrimary))
        } else {
            textView.setBackgroundResource(R.drawable.bg_green_shape_corner_6)
            textView.setTextColor(ContextCompat.getColor(activity, R.color.white))
        }
        return strStatusCart
    }

    fun getStrPriceOld(textView: TextView): String {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        return strPriceOld ?: ""
    }

    fun onClickButtonBack() {
        (activity as? AppCompatActivity)?.onBackPressedDispatcher?.onBackPressed()
    }

    companion object {
        @BindingAdapter("url_image")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, url: String?) {
            GlideUtils.loadUrlBanner(url, imageView)
        }

        @BindingAdapter("list_more_image")
        @JvmStatic
        fun loadListMoreImages(recyclerView: RecyclerView, list: ObservableList<Image>?) {
            val gridLayoutManager = GridLayoutManager(recyclerView.context, 2)
            recyclerView.layoutManager = gridLayoutManager

            val moreImageAdapter = list?.let { MoreImageAdapter(it) }
            recyclerView.adapter = moreImageAdapter
        }
    }

    private fun isFoodInCart(foodId: Int): Boolean {
        val list = FoodDatabase.getInstance(activity).foodDAO().checkFoodInCart(foodId)
        return list.isNotEmpty()
    }

    fun onClickAddToCart() {
        if (isFoodInCart(food.id)) return
        val binding: LayoutBottomSheetCartBinding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.layout_bottom_sheet_cart,
            null,
            false
        )
        val bottomSheetDialog = BottomSheetDialog(activity)
        bottomSheetDialog.setContentView(binding.root)

        val dialogCartViewModel = DialogCartViewModel(
            activity,
            bottomSheetDialog,
            food,
            object : IAddToCartSuccessListener {
                override fun addToCartSuccess() {
                    bottomSheetDialog.dismiss()
                    isFoodInCart.set(true)
                    strStatusCart.set(activity.getString(R.string.action_added_cart))
                    Toast.makeText(activity, "Đơn hàng đã được thêm vào giỏ", Toast.LENGTH_SHORT)
                        .show()
                    EventBus.getDefault().post(ReloadListCartEvent())
                }
            }
        )

        binding.dialogCartViewModel = dialogCartViewModel
        bottomSheetDialog.show()
    }

}
