package com.example.newfoodorder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.newfoodorder.R
import com.example.newfoodorder.constant.Constant
import com.example.newfoodorder.database.FoodDatabase
import com.example.newfoodorder.databinding.ItemCartBinding
import com.example.newfoodorder.listener.ICalculatePriceListener
import com.example.newfoodorder.listener.IClickItemCartListener
import com.example.newfoodorder.model.Food

class CartAdapter(
    private val mListFood: List<Food>,
    private val iCalculatePriceListener : ICalculatePriceListener
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(), IClickItemCartListener {

    inner class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemCartBinding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(itemCartBinding)
    }

    override fun getItemCount(): Int {
        return mListFood.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
       val food = mListFood[position]
        food.let {
            it.adapterPosition = holder.adapterPosition
            it.iClickItemCartListener = this
            holder.binding.foodModel = it
        }
    }

    override fun clickDeleteFood(context: Context?, food: Food?, position: Int) {
        showConfirmDialogDeleteFood(context!!, food!!, position)
    }

    override fun updateItemFood(context: Context?, food: Food?, position: Int) {
        FoodDatabase.getInstance(context!!).foodDAO().updateFood(food!!)
        notifyItemChanged(position)
        calculateTotalPrice(context)
    }
    private fun showConfirmDialogDeleteFood(context: Context, food: Food, position: Int) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.confirm_delete_food))
            .setMessage(context.getString(R.string.message_delete_food))
            .setPositiveButton(context.getString(R.string.delete)) { _, _ ->
                FoodDatabase.getInstance(context).foodDAO().deleteFood(food)
                mListFood.toMutableList().removeAt(position)
                notifyItemRemoved(position)
                calculateTotalPrice(context)
            }
            .setNegativeButton(context.getString(R.string.dialog_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    private fun calculateTotalPrice(context: Context) {
        val listFoodCart = FoodDatabase.getInstance(context).foodDAO().getListFoodCart()
        if (listFoodCart.isEmpty()) {
            val strZero = "0${Constant.CURRENCY}"
            iCalculatePriceListener.calculatePrice(strZero, 0)
            return
        }

        var totalPrice = 0
        for (food in listFoodCart) {
            totalPrice += food.totalPrice
        }

        val totalString = "$totalPrice${Constant.CURRENCY}"
        iCalculatePriceListener.calculatePrice(totalString, totalPrice)
    }

}