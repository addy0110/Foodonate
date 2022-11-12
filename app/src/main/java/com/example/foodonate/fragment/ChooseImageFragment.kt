package com.example.foodonate.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.foodonate.R
import com.example.foodonate.databinding.FragmentChooseImageBinding
import com.example.foodonate.model.UserModel
import com.example.foodonate.viewModel.firebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ChooseImageFragment : Fragment() {

    lateinit var binding: com.example.foodonate.databinding.FragmentChooseImageBinding
    lateinit var auth: FirebaseAuth
    lateinit var imageUri: Uri
    lateinit var viewModel : firebaseViewModel
    private var storageReference = Firebase.storage.reference
    private var imageUrl : String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentChooseImageBinding.inflate(layoutInflater,container,false)
        viewModel = ViewModelProvider(this)[firebaseViewModel::class.java]
        auth = FirebaseAuth.getInstance()

        binding.ivImage.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Update Profile Picture?")
            builder.setMessage("open gallery")
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                val gallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(gallery, 100)
            }
            builder.setNegativeButton("No") { dialogInterface, which ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
        binding.submitBt.setOnClickListener {
            viewModel.UpdateUserImage(imageUrl).invokeOnCompletion {
                viewModel.getCurrentUser()
                viewModel.user().observe(viewLifecycleOwner, Observer {
                    if(it.radio == "NGO"){
                        val action = ChooseImageFragmentDirections.actionChooseImageFragmentToNgoHomeFragment(viewModel.user)
                        findNavController().navigate(action)
                    }
                    else if(it.radio == "Restaurant"){
                        val action = ChooseImageFragmentDirections.actionChooseImageFragmentToRestaurantHomeFragment(viewModel.user)
                        findNavController().navigate(action)
                    }
                })
            }


        }
        return binding.root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            imageUri = data?.data!!
            binding.submitBt.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            Glide.with(this).load(imageUri).into(binding.ivImage)
            if(imageUri != null){
            try {
                storageReference.child("${auth?.currentUser?.uid}/ProfilePic").putFile(imageUri)
                    .addOnSuccessListener {
                        storageReference
                            .child("${auth.currentUser?.uid}/ProfilePic")
                            .downloadUrl.addOnSuccessListener {
                                imageUrl = it.toString()
                                binding.progressBar.visibility = View.GONE
                                binding.submitBt.visibility = View.VISIBLE
                            }.addOnFailureListener{
                                Log.e("@@ChooseImageFragmentii","Error",it)
                            }
                    }.addOnFailureListener{
                        Log.e("@@ChooseImageFragment","Error",it)
                    }
            }catch (e: Exception){
                    Log.e("@@ChooseImageFragment", "Error", e)

                }
            }}}

}