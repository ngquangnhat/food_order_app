package com.example.newfoodorder.viewmodel

import android.content.Context
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import com.example.newfoodorder.MyApplication
import com.example.newfoodorder.R
import com.example.newfoodorder.constant.Constant
import com.example.newfoodorder.constant.GlobalFunction
import com.example.newfoodorder.listener.ISendOrderSuccessListener
import com.example.newfoodorder.model.Food
import com.example.newfoodorder.utils.StringUtil
import com.example.newfoodorder.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.example.newfoodorder.model.Order

class DialogOrderViewModel(
    private var context: Context,
    private val bottomSheetDialog: BottomSheetDialog,
    private val listFoodInCart: ObservableList<Food>,
    var strTotalPrice: String,
    private val amount: Int,
    private val sendOrderSuccessListener: ISendOrderSuccessListener
) {
    val strName = ObservableField<String>()
    val strAddress = ObservableField<String>()
    val strPhone = ObservableField<String>()

    fun release() {
        bottomSheetDialog.dismiss()
    }

    fun getStringListFoodsOrder(): String {
        if (listFoodInCart.isEmpty()) {
            return ""
        }
        return listFoodInCart.joinToString(separator = "\n") { food ->
            "- ${food.name} (${food.getRealPrice()}${Constant.CURRENCY}) - ${context.getString(R.string.quantity)} ${food.count}"
        }
    }

    fun onClickCancel() {
        bottomSheetDialog.dismiss()
    }

    fun onClickSendOrder() {
        val name = strName.get()
        val phone = strPhone.get()
        val address = strAddress.get()

        if (StringUtil.isEmpty(name) || StringUtil.isEmpty(phone) || StringUtil.isEmpty(address)) {
            GlobalFunction.showToastMessage(
                context,
                context.getString(R.string.message_enter_infor_order)
            )
        } else {
            val id = System.currentTimeMillis()
            if (name != null && phone != null && address != null) {
                val order = Order(
                    id = id,
                    name = name,
                    phone = phone,
                    address = address,
                    amount = amount,
                    foods = getStringListFoodsOrder(),
                    payment = Constant.TYPE_PAYMENT_CASH
                )

                MyApplication.get(context).getBookingDatabaseReference()
                    .child(Utils.getDeviceId(context))
                    .child(id.toString())
                    .setValue(order) { error, _ ->
                        if (error == null) {
                            sendOrderSuccessListener.sendOrderSuccess()
                        }
                    }
            }
        }
    }
}
