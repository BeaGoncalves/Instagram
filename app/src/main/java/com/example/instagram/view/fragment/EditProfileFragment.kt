package com.example.instagram.view.fragment

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.instagram.R
import com.example.instagram.databinding.FragmentEditProfileBinding
import com.example.instagram.helper.UserFirebase
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.auth.FirebaseUser

class EditProfileFragment : Fragment() {

    private lateinit var _binding : FragmentEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater)
        return _binding.root


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var userLogado = UserFirebase.getUserLoggedData()

        val userProfile : FirebaseUser = UserFirebase.getUserAtual()
        var editText = _binding.editTextNomeEdit
        editText.setText(userProfile.displayName)


        var email = _binding.editTextEmailEdit
        _binding.editTextEmailEdit.isFocusable = false
        email.setText(userProfile.email)

        _binding.salvarAlteracoes.setOnClickListener {

            var nomeAtualizado = _binding.editTextNomeEdit.text.toString()

            //atualizar no firebase
            UserFirebase.updateUserName(editText.text.toString())


            //atualizar nome no banco de dados
            userLogado.nome = editText.text.toString()
            userLogado.updateUser()

            findNavController().navigate(EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment())

        }


    }




}