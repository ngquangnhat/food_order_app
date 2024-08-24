package com.example.newfoodorder.listener

import android.content.Context
import com.example.newfoodorder.model.Food

interface IClickItemCartListener {
    fun clickDeleteFood(context: Context?, food: Food?, position: Int)
    fun updateItemFood(context: Context?, food: Food?, position: Int)
}