package com.example.foodonate.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.foodonate.R
import com.example.foodonate.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        binding.singUpBt.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_signInFragment_to_registerFragment)
        }
        return binding.root
    }
}