package com.example.foodonate.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.foodonate.R
import com.example.foodonate.databinding.FragmentIntro2Binding
import com.example.foodonate.viewModel.firebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import androidx.lifecycle.Observer

class Intro2Fragment : Fragment() {
    lateinit var auth:FirebaseAuth
    lateinit var binding: FragmentIntro2Binding
    lateinit var viewModel: firebaseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntro2Binding.inflate(layoutInflater, container, false)
        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this)[firebaseViewModel::class.java]
        binding.startBt.setOnClickListener {
            if(auth.currentUser != null){
                viewModel.getCurrentUser()
                viewModel.user().observe(viewLifecycleOwner, Observer {
                    if(it.radio == "NGO"){
                        val action = Intro2FragmentDirections.actionIntro2FragmentToNgoHomeFragment(viewModel.user)
                        findNavController().navigate(action)
                    }
                    else if(it.radio == "Restaurant"){
                        val action = Intro2FragmentDirections.actionIntro2FragmentToRestaurantHomeFragment(viewModel.user)
                        findNavController().navigate(action)
                    }
                })
            }
            else{
            view?.findNavController()?.navigate(R.id.action_intro2Fragment_to_signInFragment)
            }
        }
        return binding.root
    }
}