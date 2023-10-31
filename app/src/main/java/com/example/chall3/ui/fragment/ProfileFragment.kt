package com.example.chall3.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.chall3.R
import com.example.chall3.databinding.FragmentProfileBinding
import com.example.chall3.ui.login.LoginActivity
import com.example.chall3.ui.login.LoginViewModel
import com.example.chall3.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val homeViewModel: HomeViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)

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
            loginViewModel.logout()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finishAffinity()
            Toast.makeText(requireContext(), getString(R.string.logout_succed), Toast.LENGTH_SHORT)
                .show()
        }
    }
}
