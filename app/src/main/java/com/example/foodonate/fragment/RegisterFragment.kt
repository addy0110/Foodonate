package com.example.foodonate.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.foodonate.R
import com.example.foodonate.databinding.FragmentRegisterBinding
import com.example.foodonate.model.UserModel
import com.example.foodonate.viewModel.firebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    lateinit var auth : FirebaseAuth
    private var db = Firebase.firestore.collection("Users")
    lateinit var viewModel : firebaseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        binding = FragmentRegisterBinding.inflate(layoutInflater,container,false)
        viewModel = ViewModelProvider(this)[firebaseViewModel::class.java]
        binding.submitBt.setOnClickListener {
            val email = binding.etEmailId.text.toString()
            val password = binding.etPassword.text.toString()
            val name = binding.etName.text.toString()
            val phoneNumber = binding.etPhNumber.text.toString()
            var radio: String? = null
            if(binding.rgOption.checkedRadioButtonId == binding.radioNGO.id){
                radio = "NGO"
            }else if(binding.rgOption.checkedRadioButtonId == binding.radioRestaurant.id){
                radio = "Restaurant"
            }
             binding.rgOption.checkedRadioButtonId
            Log.d("@@RegisterFragment", binding.rgOption.checkedRadioButtonId.toString() + binding.radioNGO.id)

            if(email.isEmpty() || password.isEmpty() || name.isEmpty() || phoneNumber.isEmpty() || radio == null){
                Toast.makeText(context,"Please select all the fields", Toast.LENGTH_LONG).show()
                Log.d("@@RegisterFragment", "${email}, $password, $name, $radio, $phoneNumber")
            }else{
                register(email,password,name,phoneNumber,radio!!)
            }

        }
        return binding.root
    }
    private fun register(email:String,password:String,name:String,phoneNumber:String,radio:String)= CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                addUser(name, phoneNumber, radio)

                view?.findNavController()?.navigate(R.id.action_registerFragment_to_chooseImageFragment)
            }catch (e : Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context,e.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun addUser(name:String,phoneNumber:String,radio:String) = CoroutineScope(Dispatchers.IO).launch {
        val newUser = UserModel(auth.uid,name, phoneNumber, radio)
        try {
            db.add(newUser).await()

        }catch (e : Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(context,e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}