package com.example.foodonate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.foodonate.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.checkerframework.checker.units.qual.Length

class SignInFragment : Fragment() {
    lateinit var binding: FragmentSignInBinding
    lateinit var auth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        binding = FragmentSignInBinding.inflate(layoutInflater,container,false)
        binding.singUpBt.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_signInFragment_to_registerFragment)
        }
        binding.btLogin.setOnClickListener {
            val email = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            signIn(email,password)

        }
        return binding.root
    }

    private fun signIn(email :String, password : String) = CoroutineScope(Dispatchers.IO).launch {
        if(email.isNotEmpty() && password.isNotEmpty()){
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                Log.d("@@SignInFragment", "Sign")
            }catch (e : Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context,e.message, Toast.LENGTH_LONG).show()
                }
            }
        }else{
            withContext(Dispatchers.Main){
                Toast.makeText(context,"Email and password can't be empty", Toast.LENGTH_LONG).show()
            }
        }
    }
}