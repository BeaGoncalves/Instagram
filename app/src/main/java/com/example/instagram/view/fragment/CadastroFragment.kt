package com.example.instagram.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.instagram.databinding.FragmentCadastroBinding
import com.example.instagram.helper.FirebaseConfig
import com.example.instagram.helper.UserFirebase
import com.example.instagram.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DatabaseReference

class CadastroFragment : Fragment() {


    private lateinit var binding : FragmentCadastroBinding

    private lateinit var auth : FirebaseAuth



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCadastroBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.buttonCadastrarCadastro.setOnClickListener {
            verifaCampos()
        }
    }

    private fun verifaCampos() {
        val nome = binding.editTextNameCadastro.text.toString()
        val email = binding.editTextEmailCadastro.text.toString()
        val senha = binding.editTextPasswordCadastrod.text.toString()
        val texto = String()


        if (!nome.isEmpty()){
            if (!email.isEmpty()){
                if(!senha.isEmpty()){
                   val user = User()
                    user.nome = nome
                    user.email = email
                    cadastro(user, senha)
                }
                else {
                    Toast.makeText(requireContext(), "preencha o campo de senha!", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(requireContext(), "preencha o campo email!", Toast.LENGTH_SHORT).show()
            }
        }
        else Toast.makeText(requireContext(), "preencha o campo nome", Toast.LENGTH_SHORT).show()
    }

    private fun cadastro(user: User,senha : String) {
        auth = FirebaseConfig.getInstanceAuth()
        auth.createUserWithEmailAndPassword(
            user.email,
            senha
        ).addOnCompleteListener { task ->
            if (task.isSuccessful){
               try {

                   binding.progressBar.visibility = View.GONE

                   var idUser = task.result.user!!.uid
                       user.id = idUser

                   user.saveUser()

                   UserFirebase.updateUserName(user.nome)



                   findNavController().navigate(
                       CadastroFragmentDirections.actionCadastroFragmentToFeedFragment()
                   )
               } catch (exception : Exception) {
                   Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT).show()
               }
            } else {
                binding.progressBar.visibility = View.GONE
                var msgError = ""
                try {
                    throw task.exception!!
                } catch (e : FirebaseAuthWeakPasswordException) {
                    msgError = "Digite uma senha mais forte"
                } catch (e : FirebaseAuthInvalidCredentialsException) {
                    msgError = "Digite um e-mail válido"
                } catch ( e: FirebaseAuthUserCollisionException) {
                    msgError = "Essa conta já está sendo utilizada"
                } catch (e : java.lang.Exception) {
                    msgError = "ao cadastrar usuário: ${e.message}"
                    e.printStackTrace()
                }
                Toast.makeText(requireContext(), msgError, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveUser(user: User){


    }

}