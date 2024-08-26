package com.example.newfoodorder.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newfoodorder.R
import com.example.newfoodorder.adapter.ContactAdapter
import com.example.newfoodorder.constant.AboutUsConfig
import com.example.newfoodorder.model.Contact

class ContactViewModel(
    var context: Context
) {
    var aboutUsTitle:ObservableField<String> = ObservableField(AboutUsConfig.ABOUT_US_TITLE)
    var aboutUsContent:ObservableField<String> = ObservableField(AboutUsConfig.ABOUT_US_CONTENT)
    var aboutUsWebsite:ObservableField<String> = ObservableField(AboutUsConfig.ABOUT_US_WEBSITE_TITLE)
    var listContact: ObservableArrayList<Contact> = ObservableArrayList()
    init {
        getListContacts()
    }
    private fun getListContacts(){
        listContact.add(Contact(Contact.FACEBOOK, R.drawable.ic_facebook, "Facebook"))
        listContact.add(Contact(Contact.HOTLINE, R.drawable.ic_hotline, "Hotline"))
        listContact.add(Contact(Contact.GMAIL, R.drawable.ic_gmail, "Gmail"))
        listContact.add(Contact(Contact.SKYPE, R.drawable.ic_skype, "Skype"))
        listContact.add(Contact(Contact.YOUTUBE, R.drawable.ic_youtube, "Youtube"))
        listContact.add(Contact(Contact.ZALO, R.drawable.ic_zalo, "Zalo"))
    }
    companion object{
        @BindingAdapter("list_data")
        @JvmStatic
        fun loadListContact(recyclerView: RecyclerView, listContact: ObservableArrayList<Contact>){
            val gridLayoutManager : GridLayoutManager = GridLayoutManager(recyclerView.context, 3)
            recyclerView.layoutManager = gridLayoutManager
            val contactAdapter = ContactAdapter(listContact)
            recyclerView.adapter = contactAdapter
        }
    }
    fun onClickWebsite(){
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AboutUsConfig.WEBSITE)))
    }
}