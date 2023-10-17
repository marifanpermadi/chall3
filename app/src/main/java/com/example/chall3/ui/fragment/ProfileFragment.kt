package com.example.chall3.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.chall3.R
import com.example.chall3.database.users.UserDatabase
import com.example.chall3.databinding.FragmentProfileBinding
import com.example.chall3.ui.login.LoginActivity
import com.example.chall3.viewmodel.HomeViewModel
import com.example.chall3.viewmodelfactory.HomeViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(layoutInflater)

        auth = Firebase.auth

        val userDatabase = UserDatabase.getUserDataBase(requireContext())
        val userDao = userDatabase.userDao()
        homeViewModel = ViewModelProvider(requireActivity(), HomeViewModelFactory(userDao))[HomeViewModel::class.java]

        checkUser()
        logOut()

        return binding.root

    }

    private fun checkUser() {
        homeViewModel.getUserByEmail()
        homeViewModel.userLiveData.observe(viewLifecycleOwner) {
            binding.userName.text = it.userName
            binding.email.text = it.email
            binding.phone.text = it.phoneNumber
        }
    }

    private fun logOut() {
        binding.ivLogout.setOnClickListener {
            Firebase.auth.signOut()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finishAffinity()
            Toast.makeText(requireContext(), getString(R.string.logout_succed), Toast.LENGTH_SHORT)
                .show()
        }
    }
}
