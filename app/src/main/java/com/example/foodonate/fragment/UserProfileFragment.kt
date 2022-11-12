package com.example.foodonate.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodonate.databinding.FragmentUserProfileBinding

class UserProfileFragment : Fragment() {

    lateinit var binding: FragmentUserProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileBinding.inflate(layoutInflater, container, false)
        binding.openEditPage.setOnClickListener {
            //view?.findNavController()?.navigate(R.id.action_signInFragment_to_registerFragment)
        }

        binding.openAboutPage.setOnClickListener {
            //view?.findNavController()?.navigate(R.id.action_signInFragment_to_registerFragment)
        }

        binding.openLogOut.setOnClickListener {
        }
        return binding.root
    }
}