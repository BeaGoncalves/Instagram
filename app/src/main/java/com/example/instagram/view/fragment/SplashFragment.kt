package com.example.instagram.view.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.instagram.R
import com.example.instagram.helper.FirebaseConfig
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.coroutineContext

class SplashFragment : Fragment() {


    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        verificaUserLogado()

    }


    private fun verificaUserLogado(){
        Handler().postDelayed({
        auth = FirebaseConfig.getInstanceAuth()
        if (auth.currentUser != null ) {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToFeedFragment())
        } else {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        }
        }, 5000)
    }

}