package com.example.instagram.view.activity

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.instagram.R
import com.example.instagram.databinding.ActivityMainBinding
import com.example.instagram.helper.FirebaseConfig
import com.example.instagram.view.CustomToolbar
import com.example.instagram.view.fragment.EditProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var _binding : ActivityMainBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var toolbar : androidx.appcompat.widget.Toolbar
    private lateinit var bottomNavigation : BottomNavigationView


    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(_binding.navHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.feedFragment, R.id.profileFragment, R.id.pesquisarFragment, R.id.criarFragment, R.id.notificacoesFragment))


        _binding.toolbarMainActivity.setupWithNavController(navController, appBarConfiguration)



        bottomNavigation = _binding.bottomNavigationMain
        bottomNavigation.itemTextAppearanceActive = View.GONE
         bottomNavigation.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->


            when(destination.id) {
                R.id.loginFragment -> ocultarBottomandToolbar()
                R.id.cadastroFragment -> ocultarBottomandToolbar()
                R.id.splashFragment -> ocultarBottomandToolbar()
                R.id.editProfileFragment -> bottomNavigation.visibility = View.GONE
                else -> mostrarBottomandToolbar()
            }
        }

    }


    private fun mostrarBottomandToolbar() {
        bottomNavigation.visibility = View.VISIBLE
        _binding.toolbarMainActivity.visibility = View.VISIBLE
    }

    private fun ocultarBottomandToolbar() {
        bottomNavigation.visibility = View.GONE
        _binding.toolbarMainActivity.visibility = View.GONE
    }

    private fun signOut() {
        auth = FirebaseConfig.getInstanceAuth()
        try {
            auth.signOut()
        } catch (exception : java.lang.Exception) {
            Toast.makeText(this, exception.message.toString(), Toast.LENGTH_SHORT).show()
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

}