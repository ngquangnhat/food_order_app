package com.example.newfoodorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newfoodorder.databinding.ItemFoodPopularBinding
import com.example.newfoodorder.model.Food

class FoodPopularAdapter(private val mListFoods: List<Food>) : RecyclerView.Adapter<FoodPopularAdapter.FoodPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodPopularViewHolder {
        val itemFoodPopularBinding = ItemFoodPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodPopularViewHolder(itemFoodPopularBinding)
    }

    override fun onBindViewHolder(holder: FoodPopularViewHolder, position: Int) {
        val food = mListFoods.getOrNull(position) ?: return
        holder.itemFoodPopularBinding.foodModel = food
    }

    override fun getItemCount(): Int {
        return mListFoods.size
    }

    class FoodPopularViewHolder(val itemFoodPopularBinding: ItemFoodPopularBinding) : RecyclerView.ViewHolder(itemFoodPopularBinding.root)
}
