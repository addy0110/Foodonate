package com.example.foodonate.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodonate.R
import com.example.foodonate.databinding.FragmentIntro2Binding
import com.example.foodonate.databinding.FragmentRestaurantHomeBinding

class RestaurantHomeFragment : Fragment() {
    private val args : RestaurantHomeFragmentArgs by navArgs()
    private lateinit var binding: FragmentRestaurantHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRestaurantHomeBinding.inflate(layoutInflater,container,false)
        val user = args.user
        binding.tvWelcomeRestaurantName.text = user.name
        Glide.with(requireContext()).load(user.profileUrl).into(binding.ivRes)
        Log.d("resHome",args.user.toString())

        binding.btMakeDonation.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_restaurantHomeFragment_to_makeDonationFragment)
        }
        return binding.root
    }
}