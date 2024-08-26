package com.example.newfoodorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newfoodorder.databinding.ItemContactBinding
import com.example.newfoodorder.model.Contact

class ContactAdapter( private val listContact: List<Contact>): RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(private val contactBinding: ItemContactBinding): RecyclerView.ViewHolder(contactBinding.root){
        fun bind(contact: Contact) {
            contactBinding.contactModel = contact
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val contactBinding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(contactBinding)
    }

    override fun getItemCount(): Int = listContact.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = listContact.getOrNull(position) ?: return
        holder.bind(contact)
    }

}