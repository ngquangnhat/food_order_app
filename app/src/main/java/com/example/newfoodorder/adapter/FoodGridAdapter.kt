package com.example.newfoodorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newfoodorder.databinding.ItemFoodGridBinding
import com.example.newfoodorder.model.Food

class FoodGridAdapter(private val mListFoods: List<Food>) : RecyclerView.Adapter<FoodGridAdapter.FoodGridViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodGridViewHolder {
        val itemFoodGridBinding = ItemFoodGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodGridViewHolder(itemFoodGridBinding)
    }

    override fun onBindViewHolder(holder: FoodGridViewHolder, position: Int) {
        val food = mListFoods.getOrNull(position) ?: return
        holder.itemFoodGridBinding.foodModel = food
    }

    override fun getItemCount(): Int {
        return mListFoods.size
    }

    class FoodGridViewHolder(val itemFoodGridBinding: ItemFoodGridBinding) : RecyclerView.ViewHolder(itemFoodGridBinding.root)
}
