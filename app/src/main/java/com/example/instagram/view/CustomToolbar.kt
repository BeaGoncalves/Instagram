package com.example.instagram.view

import android.annotation.SuppressLint
import android.app.Application
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.instagram.R
import com.example.instagram.helper.FirebaseConfig
import com.example.instagram.view.fragment.LoginFragment
import com.google.firebase.auth.FirebaseAuth

open class CustomToolbar : AppCompatActivity(){

    private lateinit var auth : FirebaseAuth
    private lateinit var toolbar : androidx.appcompat.widget.Toolbar
    val isEnable = false




    private fun signOut() {
        auth = FirebaseConfig.getInstanceAuth()
        try {
            auth.signOut()
        } catch (exception : java.lang.Exception) {
            Toast.makeText(this, exception.message.toString(), Toast.LENGTH_SHORT).show()
        }

    }
}