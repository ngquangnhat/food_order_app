package com.example.newfoodorder.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.newfoodorder.model.Food

@Dao
interface FoodDAO {
    @Insert
    fun insertFood(food: Food)

    @Query("SELECT * FROM food")
    fun getListFoodCart(): List<Food>

    @Query("SELECT * FROM food WHERE id=:id")
    fun checkFoodInCart(id: Int): List<Food>

    @Delete
    fun deleteFood(food: Food)

    @Update
    fun updateFood(food: Food)

    @Query("DELETE from food")
    fun deleteAllFood()


}