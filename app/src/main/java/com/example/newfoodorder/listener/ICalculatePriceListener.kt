package com.example.newfoodorder.listener

interface ICalculatePriceListener {
    fun calculatePrice(totalPrice: String?, amount: Int)
}