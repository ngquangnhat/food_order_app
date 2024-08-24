package com.example.newfoodorder.model

import com.example.newfoodorder.constant.Constant
import com.example.newfoodorder.utils.DateTimeUtils
import java.io.Serializable

data class Order(
    var id: Long = 0,
    var name: String = "",
    var phone: String = "",
    var address: String = "",
    var amount: Int = 0,
    var foods: String = "",
    var payment: Int = Constant.TYPE_PAYMENT_CASH
) : Serializable {

    fun getStringId(): String {
        return id.toString()
    }

    fun getStringDate(): String {
        return DateTimeUtils.convertTimeStampToDate(id)
    }

    fun getStringAmount(): String {
        return "$amount${Constant.CURRENCY}"
    }

    fun getStringPaymentMethod(): String {
        return if (payment == Constant.TYPE_PAYMENT_CASH) {
            Constant.PAYMENT_METHOD_CASH
        } else {
            ""
        }
    }
}
