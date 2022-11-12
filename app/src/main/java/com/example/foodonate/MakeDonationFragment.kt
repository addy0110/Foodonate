package com.example.foodonate

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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.foodonate.databinding.FragmentMakeDonationBinding
import com.example.foodonate.model.PostModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

class MakeDonationFragment : Fragment() {

    lateinit var binding:FragmentMakeDonationBinding
    lateinit var imageUri : Uri
    lateinit var auth : FirebaseAuth
    private var db = Firebase.firestore.collection("Donations")
    private var storageReference = Firebase.storage.reference
    private var imageUrl : String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        binding = FragmentMakeDonationBinding.inflate(layoutInflater,container,false)
        binding.ivFoodImgCd.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Add Donation Picture")
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
        binding.btSubmit.setOnClickListener {

            val name = binding.etItemName.text.toString()
            val timeOfPrep = binding.etTimeOfPrep.text.toString()
            val quantity = binding.etQuantity.text.toString()
            val address = binding.etAddress.text.toString()
            var radio : String? = ""
            if(binding.rgUtensils.checkedRadioButtonId == binding.radioYes.id){
                radio = "Yes"
            }else if(binding.rgUtensils.checkedRadioButtonId == binding.radioNo.id){
                radio = "No"
            }
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(Date())
            if(name.isNotEmpty() || timeOfPrep.isNotEmpty() || quantity.isNotEmpty() || address.isNotEmpty() || radio?.isNotEmpty()!! || imageUrl.isNotEmpty()){
                val postM = PostModel(name,timeOfPrep,quantity,address,radio,currentDate,imageUrl)
                post(postM)
            }else{
                Toast.makeText(context,"Please fill all fields",Toast.LENGTH_LONG).show()
            }
            view?.findNavController()?.navigate(R.id.action_makeDonationFragment_to_restaurantHomeFragment)

        }

        return binding.root
    }
    private fun post(postM : PostModel) = CoroutineScope(Dispatchers.IO).launch {
        try {
            db.add(postM)
        }catch (e: Exception){
            Log.e("@@MakeDonationFragment","Error",e)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            imageUri = data?.data!!
            binding.btSubmit.visibility = View.GONE
            binding.progressBar2.visibility = View.VISIBLE
            Glide.with(this).load(imageUri).into(binding.ivFoodIv)
            if(imageUri != null){
                try {
                    storageReference.child("Donations/${auth.currentUser?.uid}").putFile(imageUri)
                        .addOnSuccessListener {
                            storageReference
                                .child("Donations/${auth.currentUser?.uid}")
                                .downloadUrl.addOnSuccessListener {
                                    imageUrl = it.toString()
                                    binding.progressBar2.visibility = View.GONE
                                    binding.btSubmit.visibility = View.VISIBLE
                                }.addOnFailureListener{
                                    Log.e("@@MakeDonationFragment","Error",it)
                                }
                        }.addOnFailureListener{
                            Log.e("@@MakeDonationFragment","Error",it)
                        }
                }catch (e: Exception){
                    Log.e("@@MakeDonationFragment", "Error", e)

                }
            }
        }
    }


}