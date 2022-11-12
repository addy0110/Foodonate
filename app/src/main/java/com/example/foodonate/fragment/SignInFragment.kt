package com.example.foodonate.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.foodonate.R
import com.example.foodonate.databinding.FragmentSignInBinding
import com.example.foodonate.viewModel.firebaseViewModel
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
    lateinit var viewModel: firebaseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        binding = FragmentSignInBinding.inflate(layoutInflater,container,false)
        viewModel = ViewModelProvider(this)[firebaseViewModel::class.java]
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

    private fun signIn(email :String, password : String) = CoroutineScope(Dispatchers.Main).launch {
        if(email.isNotEmpty() && password.isNotEmpty()){
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                viewModel.getCurrentUser()
                viewModel.user().observe(viewLifecycleOwner, Observer {
                    Log.d("radio",it.radio.toString())
                    if(it.radio == "NGO"){
                        val action = SignInFragmentDirections.actionSignInFragmentToNgoHomeFragment(viewModel.user)
                        findNavController().navigate(action)
                    }
                    else if(it.radio == "Restaurant"){
                        val action = SignInFragmentDirections.actionSignInFragmentToRestaurantHomeFragment(viewModel.user)
                        findNavController().navigate(action)
                    }
                })
//                if(viewModel.user().value?.radio == "NGO"){
//                    val action = SignInFragmentDirections.actionSignInFragmentToNgoHomeFragment(viewModel.user)
//                    findNavController().navigate(action)
//                }
//                else{
//                    val action = SignInFragmentDirections.actionSignInFragmentToRestaurantHomeFragment(viewModel.user)
//                    findNavController().navigate(action)
//                }
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