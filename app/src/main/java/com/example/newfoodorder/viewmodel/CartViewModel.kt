package com.example.newfoodorder.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newfoodorder.R
import com.example.newfoodorder.adapter.CartAdapter
import com.example.newfoodorder.constant.Constant
import com.example.newfoodorder.constant.GlobalFunction
import com.example.newfoodorder.database.FoodDatabase
import com.example.newfoodorder.databinding.LayoutBottomSheetOrderBinding
import com.example.newfoodorder.listener.ICalculatePriceListener
import com.example.newfoodorder.listener.ISendOrderSuccessListener
import com.example.newfoodorder.model.Food
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class CartViewModel(private val context: Context) {
    var listFoodInCart: ObservableList<Food> = ObservableArrayList()
    companion object {
        var strTotalPrice: String? = null
        var mAmount: Int = 0
        private var cartAdapter: CartAdapter? = null
        @BindingAdapter("list_cart", "calculate_price")
        @JvmStatic
        fun loadListFoodInCart(recyclerView: RecyclerView, list: ObservableList<Food>, textView: TextView) {
            val linearLayoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.layoutManager = linearLayoutManager
            val itemDecoration = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
            recyclerView.addItemDecoration(itemDecoration)

            cartAdapter = CartAdapter(list, object : ICalculatePriceListener {
                override fun calculatePrice(totalPrice: String?, amount: Int) {
                    textView.text = totalPrice
                    strTotalPrice = totalPrice
                    mAmount = amount
                }
            })
            strTotalPrice = getValueTotalPrice(recyclerView.context)
            textView.text = strTotalPrice
            recyclerView.adapter = cartAdapter
        }
        fun getValueTotalPrice(context: Context): String {
            val listFoodCart = FoodDatabase.getInstance(context).foodDAO().getListFoodCart()
            if (listFoodCart.isEmpty()) {
                mAmount = 0
                return "0${Constant.CURRENCY}"
            }

            var totalPrice = 0
            for (food in listFoodCart) {
                totalPrice += food.totalPrice
            }

            mAmount = totalPrice
            return "$totalPrice${Constant.CURRENCY}"
        }


    }
    private var dialogOrderViewModel: DialogOrderViewModel? = null

    init {
        getListFoodInCartOK()
    }

    fun getListFoodInCartOK() {
        listFoodInCart.clear()
        val list = FoodDatabase.getInstance(context).foodDAO().getListFoodCart()
        listFoodInCart.addAll(list)
    }
    fun onClickOrderCart() {
        if (listFoodInCart.isEmpty()) {
            return
        }

        val binding = DataBindingUtil.inflate<LayoutBottomSheetOrderBinding>(
            LayoutInflater.from(context),
            R.layout.layout_bottom_sheet_order,
            null,
            false
        )
        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

        dialogOrderViewModel = DialogOrderViewModel(
            context, bottomSheetDialog, listFoodInCart,
            strTotalPrice ?: "", mAmount, object : ISendOrderSuccessListener {
                override fun sendOrderSuccess() {
                    GlobalFunction.showToastMessage(context, context.getString(R.string.msg_order_success))
                    GlobalFunction.hideSoftKeyboard(context as Activity)
                    bottomSheetDialog.dismiss()

                    FoodDatabase.getInstance(context).foodDAO().deleteAllFood()
                    clearCart()
                }

            }
        )
        binding.dialogOrderViewModel = dialogOrderViewModel
        bottomSheetDialog.show()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun clearCart() {
        listFoodInCart.clear()
        cartAdapter?.notifyDataSetChanged()
    }
    fun release() {
        dialogOrderViewModel?.release()
    }

}