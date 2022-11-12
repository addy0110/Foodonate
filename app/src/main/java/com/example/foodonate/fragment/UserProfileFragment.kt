package com.example.foodonate.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.foodonate.R
import com.example.foodonate.databinding.FragmentUserProfileBinding

class UserProfileFragment : Fragment() {

    lateinit var binding: FragmentUserProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileBinding.inflate(layoutInflater, container, false)
        binding.openEditPage.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_profileFragment_to_editFragment)
        }

        binding.openAboutPage.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_profileFragment_to_aboutFragment)
        }

        binding.LogOut.setOnClickListener {
        }
        return binding.root
    }

}