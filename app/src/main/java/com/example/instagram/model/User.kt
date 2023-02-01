package com.example.instagram.model

import com.example.instagram.helper.FirebaseConfig
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude
import java.util.Objects

data class User(
    var id: String = "",
    var nome: String = "",
    var email: String = "",
    var caminhoFoto: String = "",
) {

    fun saveUser(){
        var dataBaseReference : DatabaseReference = FirebaseConfig.getInstanceDataBase()
        val userRef = dataBaseReference.child("usuarios").child(id)
        userRef.setValue(this)
    }

    fun updateUser() {
        val firebaseRef : DatabaseReference = FirebaseConfig.getInstanceDataBase()
        val usresRef : DatabaseReference = firebaseRef.child("usuarios").child(id)
        val valuesUser : Map<String, Any> = toMap()
        usresRef.updateChildren(valuesUser)
    }


    /**
     * função convertida de java porém ainda não sei o motivo mas
     * apagava todas as informações do realtime database quando
     * era chamada na função update
     */
    private fun converterParaMap(): Map<String, Any> {
        val userMap : HashMap<String, Any> = HashMap()
        val user = User()
        userMap.put("nome", user.nome)
        userMap.put("email", user.email)
        userMap.put("id", user.id)
        return userMap
    }

    @Exclude
    fun toMap() : Map<String, Any> {
        return mapOf(
            "id" to id,
            "nome" to nome,
            "email" to email

        )
    }
}
