package com.example.newfoodorder.viewmodel

import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newfoodorder.MyApplication
import com.example.newfoodorder.adapter.OrderAdapter
import com.example.newfoodorder.constant.GlobalFunction
import com.example.newfoodorder.model.Order
import com.example.newfoodorder.utils.Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class OrderViewModel(private val context: Context) {
    var listOrder: ObservableList<Order> = ObservableArrayList()

    init {
        getListOrders()
    }

    private fun getListOrders() {
        MyApplication.get(context).getBookingDatabaseReference().child(Utils.getDeviceId(context))
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        listOrder.clear()
                        for (dataSnapshot in snapshot.children) {
                            val order = dataSnapshot.getValue(Order::class.java)
                            listOrder.add(0, order)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                }
            )
    }

    companion object {
        @BindingAdapter("list_data")
        @JvmStatic
        fun loadListOrder(recyclerView: RecyclerView, listOrder: ObservableList<Order>) {
            val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.layoutManager = linearLayoutManager
            val orderAdapter = OrderAdapter(listOrder)
            recyclerView.adapter = orderAdapter
        }
    }
}