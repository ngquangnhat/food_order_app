package com.example.newfoodorder.model

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.newfoodorder.R
import com.example.newfoodorder.BR
import com.example.newfoodorder.constant.Constant
import com.example.newfoodorder.constant.GlobalFunction
import com.example.newfoodorder.listener.IClickItemCartListener
import com.example.newfoodorder.utils.GlideUtils
import com.example.newfoodorder.view.activity.FoodDetailActivity

import java.io.Serializable

@Entity(tableName = "food")
data class Food(
    @PrimaryKey
    var id: Int = 0,
    var name: String? = null,
    var image: String? = null,
    var banner: String? = null,
    var description: String? = null,
    var price: Int = 0,
    var sale: Int = 0,
    @Bindable
    var count: Int = 0,
    var totalPrice: Int = 0,
    var popular: Boolean = false,
    @Ignore
    var images: List<Image>? = null,
    @Ignore
    var adapterPosition: Int = 0,
    @Ignore
    var iClickItemCartListener: IClickItemCartListener? = null
) : BaseObservable(), Serializable {

    fun getRealPrice(): Int {
        return if (sale <= 0) price else price - (price * sale / 100)
    }

    fun getStringSale(textView: TextView): String {
        return textView.context.getString(R.string.label_discount) + " " + sale + "%"
    }

    fun isSaleOff(): Boolean {
        return sale > 0
    }

    fun getStringOldPrice(textView: TextView): String {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        return "$price${Constant.CURRENCY}"
    }

    fun getStringRealPrice(): String {
        return if (isSaleOff()) {
            "${getRealPrice()}${Constant.CURRENCY}"
        } else {
            "$price${Constant.CURRENCY}"
        }
    }

    fun getStringCount(): String {
        return count.toString()
    }

    fun goToFoodDetail(view: View) {
        val bundle = Bundle().apply {
            putSerializable(Constant.KEY_INTENT_FOOD_OBJECT, this@Food)
        }
        GlobalFunction.startActivity(view.context, FoodDetailActivity::class.java, bundle)
    }

    fun onClickButtonSubtract(view: View) {
        if (count <= 1) return
        val newCount = count - 1
        val totalPrice = getRealPrice() * newCount
        count = newCount
        this.totalPrice = totalPrice
        notifyPropertyChanged(BR.count)
        iClickItemCartListener?.updateItemFood(view.context, this, adapterPosition)
    }

    fun onClickButtonAdd(view: View) {
        val newCount = count + 1
        val totalPrice = getRealPrice() * newCount
        count = newCount
        this.totalPrice = totalPrice
        notifyPropertyChanged(BR.count)
        iClickItemCartListener?.updateItemFood(view.context, this, adapterPosition)
    }

    fun onClickButtonDelete(view: View) {
        iClickItemCartListener?.clickDeleteFood(view.context, this, adapterPosition)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("banner_image")
        fun loadImageBannerFromUrl(imageView: ImageView, url: String?) {
            GlideUtils.loadUrlBanner(url, imageView)
        }

        @JvmStatic
        @BindingAdapter("normal_image")
        fun loadImageFromUrl(imageView: ImageView, url: String?) {
            GlideUtils.loadUrl(url, imageView)
        }
    }
}

