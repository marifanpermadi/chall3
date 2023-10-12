package com.example.chall3.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.chall3.R
import com.example.chall3.databinding.FragmentProfileBinding
import com.example.chall3.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater)

        auth = Firebase.auth

        binding.ivProfile.setOnClickListener {

            val i = Intent(activity, LoginActivity::class.java)
            startActivity(i)
        }

        logOut()

        return binding.root

    }

    private fun logOut() {
        binding.ivLogout.setOnClickListener {
            Firebase.auth.signOut()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(requireContext(), getString(R.string.logout_succed), Toast.LENGTH_SHORT)
                .show()
        }
    }
}
