package com.example.instagram.view.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.instagram.databinding.FragmentEditProfileBinding
import com.example.instagram.helper.FirebaseConfig
import com.example.instagram.helper.UserFirebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream

class EditProfileFragment : Fragment() {

    private lateinit var _binding : FragmentEditProfileBinding
    private lateinit var storageReference : StorageReference
    private lateinit var dialog : AlertDialog

    private val requestGalery = registerForActivityResult(ActivityResultContracts.RequestPermission()) { permission ->
        if (permission){
            resultGalery.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
        } else {
            showDialogPermission()
        }
    }

    private val resultGalery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.data?.data != null) {
            val bitMap : Bitmap =
                if (Build.VERSION.SDK_INT < 28){
                    MediaStore.Images.Media.getBitmap(context?.contentResolver, result.data?.data)
                } else {
                    val source =
                        ImageDecoder.createSource(
                            context?.contentResolver!!,
                            result.data?.data!!
                    )
                    ImageDecoder.decodeBitmap(source)
                }
            _binding.profileImageEdit.setImageBitmap(bitMap)

            val baos = ByteArrayOutputStream()
            bitMap.compress(Bitmap.CompressFormat.JPEG, 70, baos)
            val dadosImagem : ByteArray = baos.toByteArray()

            val imagemRef : StorageReference = storageReference
                .child("imagens")
                .child("perfil")
                .child("<id-usuario>.jpeg")
            val uploadTask : UploadTask = imagemRef.putBytes(dadosImagem)
            uploadTask.addOnFailureListener{ it ->
                Toast.makeText(requireContext(), "Ocorreu um erro", Toast.LENGTH_SHORT).show()
        }
            uploadTask.addOnSuccessListener {
                Toast.makeText(requireContext(), "deu certo", Toast.LENGTH_SHORT).show()
            }
    }
    }

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

        storageReference = FirebaseConfig.getInstanceStorage()

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

        _binding.tvAlterarFoto.setOnClickListener {
           verificaPermissaoGaleria()


            }

    }



    private fun verificaPermissao(permissao: String ) = (ContextCompat.checkSelfPermission(requireContext(), permissao) == PackageManager.PERMISSION_GRANTED)


    private fun verificaPermissaoGaleria(){
        val permissionGalery = verificaPermissao(PERMISSAO_GALERIA)
        when {
            permissionGalery -> {
                resultGalery.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
            }
            shouldShowRequestPermissionRationale(PERMISSAO_GALERIA) -> showDialogPermission()
            else -> requestGalery.launch(PERMISSAO_GALERIA)
        }
    }

    private fun showDialogPermission() {
        val builder = AlertDialog.Builder(requireContext())
            .setTitle("Atenção")
            .setMessage("Precisamos do acesso a galeria do dispositivo, clique em Permitir para continuar")
            .setNegativeButton("nao"){ _, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("sim") {_, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", activity?.packageName, null)
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                dialog.dismiss()
            }
        dialog = builder.create()
        dialog.show()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK ) {
//            val imagem : Bitmap? = null
//            try {
//                when(requestCode){
//                    GALERY_SELECTION -> {
//                        var imagemSelecionada : Uri? = data!!.data
//                        when {
//                            Build.VERSION.SDK_INT < 29 -> MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imagemSelecionada)
//                        else -> {
//                            val source = ImageDecoder.createSource(requireActivity().contentResolver, imagemSelecionada!!)
//                            ImageDecoder.decodeBitmap(source)
//                        }
//                        }
//                    }
//                }
//                if (imagem != null) {
//                    _binding.profileImageEdit.setImageBitmap(imagem)
//                }
//
//
//            } catch (exeception : Exception) {
//                exeception.printStackTrace()
//            }
//        }
//    }

//    private fun getCapturedImage( selectedPhotoUri: Uri) : Bitmap {
//        val bitmap = when {
//            Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedPhotoUri)
//            else -> {
//                val source = ImageDecoder.createSource(requireActivity().contentResolver, selectedPhotoUri)
//                ImageDecoder.decodeBitmap(source)
//            }
//        }
//        return bitmap
//    }

 companion object {
     const val GALERY_SELECTION = 200
     private val PERMISSAO_GALERIA = android.Manifest.permission.READ_EXTERNAL_STORAGE
 }


}