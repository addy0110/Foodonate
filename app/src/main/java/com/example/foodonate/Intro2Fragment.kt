package com.example.foodonate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.foodonate.databinding.FragmentIntro2Binding

class Intro2Fragment : Fragment() {

    lateinit var binding: FragmentIntro2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntro2Binding.inflate(layoutInflater,container,false)
        binding.startBt.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_intro2Fragment_to_signInFragment)
        }
        return binding.root
    }
}