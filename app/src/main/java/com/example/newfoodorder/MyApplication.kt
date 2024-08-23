package com.example.newfoodorder

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MyApplication : Application() {

    companion object {
        const val FIREBASE_URL: String = "https://foodorder-96e61-default-rtdb.firebaseio.com"
    }

    private var mFirebaseDatabase: FirebaseDatabase? = null
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        mFirebaseDatabase =FirebaseDatabase.getInstance(FIREBASE_URL)
    }
    fun getFoodDatabaseReference() : DatabaseReference? {
        return mFirebaseDatabase?.getReference("/food")
    }
    fun getFeedbackDatabaseReference(): DatabaseReference {
        return mFirebaseDatabase!!.getReference("/feedback")
    }
    fun getBookingDatabaseReference(): DatabaseReference {
        return mFirebaseDatabase!!.getReference("/booking")
    }

}