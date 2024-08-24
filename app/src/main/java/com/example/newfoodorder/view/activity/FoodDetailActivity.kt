package com.example.newfoodorder.view.activity

import android.os.Bundle
import com.example.newfoodorder.constant.Constant
import com.example.newfoodorder.databinding.ActivityFoodDetailBinding
import com.example.newfoodorder.model.Food
import com.example.newfoodorder.view.BaseActivity
import com.example.newfoodorder.viewmodel.FoodDetailViewModel

class FoodDetailActivity : BaseActivity() {

    private lateinit var mFoodDetailViewModel: FoodDetailViewModel
    private var mFood: Food? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityFoodDetailBinding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(activityFoodDetailBinding.root)

        getDataIntent()
        mFood?.let {
            mFoodDetailViewModel = FoodDetailViewModel(this, it)
            activityFoodDetailBinding.foodDetailViewModel = mFoodDetailViewModel
        }
    }

    private fun getDataIntent() {
        val bundle = intent.extras
        if (bundle != null) {
            mFood = bundle.get(Constant.KEY_INTENT_FOOD_OBJECT) as Food?
        }
    }
}
