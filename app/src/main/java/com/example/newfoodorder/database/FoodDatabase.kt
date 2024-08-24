package com.example.newfoodorder.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.example.newfoodorder.model.Food

@Database(entities = [Food::class], version = 1)
abstract class FoodDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "food.db"
        @Volatile
        private var instance: FoodDatabase? = null

        fun getInstance(context: Context): FoodDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries().build().also { instance = it }
            }
        }
    }

    abstract fun foodDAO(): FoodDAO
}