package com.example.instagram.helper

import android.app.Activity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.sql.DatabaseMetaData

class FirebaseConfig {

    companion object {

        val FIREBASE_AUTH = FirebaseAuth.getInstance()
        val FIREBASE_DATABASE = FirebaseDatabase.getInstance().reference

        fun getInstanceAuth() : FirebaseAuth {
            return FIREBASE_AUTH
        }

        fun getInstanceDataBase() : DatabaseReference {
            return FIREBASE_DATABASE
        }




    }
}