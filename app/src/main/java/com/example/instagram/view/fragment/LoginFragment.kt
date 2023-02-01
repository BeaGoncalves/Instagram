package com.example.instagram.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.example.instagram.R
import com.example.instagram.databinding.FragmentLoginBinding
import com.example.instagram.helper.FirebaseConfig
import com.example.instagram.model.User
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()

    }


    private fun setListener() {
        binding.textViewCadastro.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCadastroFragment())
        }

        binding.button.setOnClickListener {
            setUser()

        }
    }

    private fun setUser() {
        val user = User()
        var senha = binding.editTextTextPassword.text.toString()
        if (!binding.editTextTextEmailAddress.text.isNullOrEmpty()) {
            if (!binding.editTextTextPassword.text.isNullOrEmpty()){

                user.email = binding.editTextTextEmailAddress.text.toString()
            } else {
                Toast.makeText(requireContext(), "campo email deve ser preenchido", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "senha deve ser preenchido", Toast.LENGTH_LONG).show()
        }
        login(user,senha )
    }

    private fun login( user : User, senha: String) {
        auth = FirebaseConfig.getInstanceAuth()
        auth.signInWithEmailAndPassword(user.email, senha).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToFeedFragment())
            } else {
                try {
                    task.exception
                } catch (e : java.lang.Exception){
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

}