package com.example.foodonate.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodonate.R
import com.example.foodonate.databinding.FragmentNgoHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class NgoHomeFragment : Fragment() {
    lateinit var binding: FragmentNgoHomeBinding
    lateinit var auth: FirebaseAuth
    private val args : NgoHomeFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNgoHomeBinding.inflate(layoutInflater,container,false)
        Log.d("ngohome",args.user.toString())
        val user = args.user
        binding.tvWelcomeNgoName.text = user.name
        Glide.with(requireContext()).load(user.profileUrl).into(binding.ivNgo)
        return binding.root
    }
}