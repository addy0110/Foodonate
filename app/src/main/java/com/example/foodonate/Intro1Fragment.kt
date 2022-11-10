package com.example.foodonate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.foodonate.databinding.FragmentIntro1Binding

class Intro1Fragment : Fragment() {
    lateinit var binding: FragmentIntro1Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntro1Binding.inflate(layoutInflater,container,false)
        binding.intro1bt.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_intro1Fragment_to_intro2Fragment)
        }
        return binding.root
    }

}