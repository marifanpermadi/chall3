package com.example.chall3.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chall3.R
import com.example.chall3.adapter.CartAdapter
import com.example.chall3.databinding.FragmentCartBinding
import com.example.chall3.viewmodel.CartViewModel
import com.example.chall3.viewmodel.ViewModelFactory

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        setUpCartViewModel()

        cartAdapter = CartAdapter(cartViewModel)
        binding.rvCart.setHasFixedSize(true)
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.adapter = cartAdapter

        cartViewModel.allCartItems.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.tvAwe.visibility = View.VISIBLE
                binding.tvNoItem.visibility = View.VISIBLE
                binding.ivEmpty.visibility = View.VISIBLE
                binding.rvCart.visibility = View.GONE
            } else {
                binding.tvAwe.visibility = View.GONE
                binding.tvNoItem.visibility = View.GONE
                binding.ivEmpty.visibility = View.GONE
                binding.rvCart.visibility = View.VISIBLE
                cartAdapter.setData(it)
            }

        }

        cartViewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.tvSumTotal.text = it.toString()
        }

        orderItem()
        onBackPressed()

        return binding.root
    }

    private fun setUpCartViewModel() {
        val viewModelFactory = ViewModelFactory(requireActivity().application)
        cartViewModel = ViewModelProvider(this, viewModelFactory)[CartViewModel::class.java]
    }

    private fun orderItem() {
        binding.btOrder.setOnClickListener {
            if (!cartViewModel.allCartItems.value.isNullOrEmpty()) {
                findNavController().navigate(R.id.action_cartFragment_to_orderFragment)
            } else {
                Toast.makeText(requireContext(), "Your cart is empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onBackPressed() {
        val navController = findNavController()
        requireActivity()
            .onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                navController.navigate(R.id.action_cartFragment_to_homeFragment)
            }
    }

}