package com.example.newfoodorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newfoodorder.databinding.ItemMoreImageBinding
import com.example.newfoodorder.model.Image

class MoreImageAdapter(private val listImages: List<Image>) : RecyclerView.Adapter<MoreImageAdapter.MoreImageViewHolder>() {

    inner class MoreImageViewHolder(private val binding: ItemMoreImageBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(image: Image) {
            binding.imageModel = image
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreImageViewHolder {
        val binding = ItemMoreImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoreImageViewHolder(binding)
    }

    override fun getItemCount(): Int = listImages.size

    override fun onBindViewHolder(holder: MoreImageViewHolder, position: Int) {
        val image = listImages.getOrNull(position) ?: return
        holder.bind(image)
    }

}