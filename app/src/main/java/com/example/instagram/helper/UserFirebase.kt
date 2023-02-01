package com.example.instagram.helper

import android.content.ContentValues.TAG
import android.util.Log
import com.example.instagram.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class UserFirebase {

    companion object {

        fun updateUserName( name : String){
            try {
                val user : FirebaseUser = getUserAtual()
                val profileChangeRequest = UserProfileChangeRequest
                    .Builder()
                    .setDisplayName(name)
                    .build()

                user.updateProfile(profileChangeRequest).addOnCompleteListener { 
                    if (!it.isSuccessful){
                        Log.e(TAG, "updateUserName: ${it.exception?.message}", )
                    }
                }
            } catch (exeception : Exception) {
                exeception.printStackTrace()
            }

        }
        fun getUserAtual() : FirebaseUser {
            val instance : FirebaseAuth = FirebaseConfig.getInstanceAuth()
            return instance.currentUser!!
        }

        fun getUserLoggedData() : User {
            val firebaseUser : FirebaseUser = getUserAtual()

            var user = User()
            user.id = firebaseUser.uid.toString()
            user.nome = firebaseUser.displayName.toString()
            user.email = firebaseUser.email.toString()

            if (firebaseUser.photoUrl == null){
                user.caminhoFoto = ""
            } else {
                user.caminhoFoto = firebaseUser.photoUrl.toString()
            }

            return user
        }
    }
}