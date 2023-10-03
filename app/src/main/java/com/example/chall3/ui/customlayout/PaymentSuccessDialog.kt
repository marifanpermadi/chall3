package com.example.chall3.ui.customlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.chall3.R
import com.example.chall3.databinding.FragmentPaymentSuccessBinding

class PaymentSuccessDialog : DialogFragment() {

    private lateinit var binding: FragmentPaymentSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentSuccessBinding.inflate(layoutInflater, container, false)

        backToHome()

        return binding.root
    }

    private fun backToHome() {
        binding.btHome.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }

}