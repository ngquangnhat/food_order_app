package com.example.newfoodorder.viewmodel

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.newfoodorder.MyApplication
import com.example.newfoodorder.R
import com.example.newfoodorder.adapter.FoodGridAdapter
import com.example.newfoodorder.adapter.FoodPopularAdapter
import com.example.newfoodorder.constant.GlobalFunction
import com.example.newfoodorder.model.Food
import com.example.newfoodorder.utils.StringUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import me.relex.circleindicator.CircleIndicator3

class HomeViewModel(private val context: Context) : BaseObservable() {

    val listFood = ObservableArrayList<Food>()
    val listFoodPopular = ObservableArrayList<Food>()
    val isSuccess = ObservableBoolean()
    val stringHint = ObservableField<String>()

    init {
        getListFoodFromFirebase("")
    }

    private fun getListFoodFromFirebase(key: String) {
        val app = MyApplication.get(context)
        app.getFoodDatabaseReference()?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listFood.clear()
                for (dataSnapshot in snapshot.children) {
                    val food = dataSnapshot.getValue(Food::class.java)
                    if (food != null) {
                        if (StringUtil.isEmpty(key) || food.name?.let {
                                GlobalFunction.getTextSearch(it).lowercase().trim()
                                    .contains(GlobalFunction.getTextSearch(key).lowercase().trim())
                            } == true) {
                            listFood.add(food)
                        }
                    }
                }
                getListFoodPopular(listFood)
                isSuccess.set(true)
            }

            override fun onCancelled(error: DatabaseError) {
                listFood.clear()
            }
        })
    }

    private fun getListFoodPopular(listFood: List<Food>) {
        listFoodPopular.clear()
        for (food in listFood) {
            if (food.popular) {
                listFoodPopular.add(food)
            }
        }
    }

    fun getStringHint(editText: EditText): ObservableField<String> {
        stringHint.set(context.getString(R.string.hint_search_name))

        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val keyword = editText.text.toString()
                searchFood(keyword)
                true
            } else {
                false
            }
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val strKey = s.toString().trim()
                if (strKey.isEmpty()) {
                    searchFood("")
                }
            }
        })
        return stringHint
    }

    fun onClickButtonSearch(editText: EditText) {
        val keyword = editText.text.toString()
        searchFood(keyword)
    }

    private fun searchFood(key: String) {
        listFood.clear()
        getListFoodFromFirebase(key)
    }

    companion object {
        @BindingAdapter("list_data")
        @JvmStatic
        fun loadListFood(recyclerView: RecyclerView, list: ObservableList<Food>?) {
            GlobalFunction.hideSoftKeyboard(recyclerView.context as Activity)
            if (list == null) {
                GlobalFunction.showToastMessage(recyclerView.context, recyclerView.context.getString(R.string.msg_get_date_error))
                return
            }
            val gridLayoutManager = GridLayoutManager(recyclerView.context, 2)
            recyclerView.layoutManager = gridLayoutManager
            val foodGridAdapter = FoodGridAdapter(list)
            recyclerView.adapter = foodGridAdapter
        }

        @BindingAdapter(value = ["list_data_popular", "indicator_viewpager"])
        @JvmStatic
        fun loadListFoodPopular(viewPager2: ViewPager2, list: ObservableList<Food>?, indicator3: CircleIndicator3) {
            val foodPopularAdapter = FoodPopularAdapter(list ?: emptyList())
            viewPager2.adapter = foodPopularAdapter
            val handlerBanner = Handler(Looper.getMainLooper())
            val runnableBanner = object : Runnable {
                override fun run() {
                    if (list.isNullOrEmpty()) return
                    if (viewPager2.currentItem == list.size - 1) {
                        viewPager2.currentItem = 0
                        return
                    }
                    viewPager2.currentItem += 1
                }
            }
            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    handlerBanner.removeCallbacks(runnableBanner)
                    handlerBanner.postDelayed(runnableBanner, 3000)
                }
            })
            indicator3.setViewPager(viewPager2)
        }
    }

    fun release() {
        // Clean up resources if needed
    }
}

