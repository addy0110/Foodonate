package com.example.foodonate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodonate.databinding.FragmentDonationRequestBinding

class DonationRequestFragment : Fragment() {

    lateinit var binding: FragmentDonationRequestBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDonationRequestBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

}