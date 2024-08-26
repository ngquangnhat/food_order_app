package com.example.newfoodorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newfoodorder.databinding.ItemOrderBinding
import com.example.newfoodorder.model.Order

class OrderAdapter(private val listOrder: List<Order>): RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(private val binding: ItemOrderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(order: Order) {
            binding.orderModel = order
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listOrder.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = listOrder.getOrNull(position) ?: return
        holder.bind(order)
    }
}