package com.example.chall3.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chall3.databinding.FragmentProfileBinding
import com.example.chall3.ui.login.LoginActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater)


        binding.ivProfile.setOnClickListener {

            val i = Intent(activity, LoginActivity::class.java)
            startActivity(i)
        }

        return binding.root

    }
}
