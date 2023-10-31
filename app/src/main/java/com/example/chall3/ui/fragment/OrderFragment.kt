package com.example.chall3.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chall3.R
import com.example.chall3.adapter.CartAdapter
import com.example.chall3.data.apimodel.OrderItem
import com.example.chall3.data.apimodel.OrderRequest
import com.example.chall3.databinding.FragmentOrderBinding
import com.example.chall3.ui.customlayout.PaymentSuccessDialog
import com.example.chall3.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    private lateinit var cartAdapter: CartAdapter

    private val cartViewModel: CartViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater, container, false)

        cartAdapter = CartAdapter(cartViewModel)
        binding.rvOrder.setHasFixedSize(true)
        binding.rvOrder.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrder.adapter = cartAdapter

        observeTotalPrice()
        observeOrderRequest()
        observeLoading()
        observeCartItems()

        deliveryMethod()
        paymentMethod()
        payNow()

        return binding.root
    }

    private fun payNow() {
        binding.btPay.setOnClickListener {

            val orderItems = cartViewModel.allCartItems.value ?: emptyList()
            if (orderItems.isNotEmpty()) {
                val total = cartViewModel.totalPrice.value ?: 0
                val orderRequest = OrderRequest("Uchup", total, orderItems.map {
                    OrderItem(it.foodName, it.orderAmount, it.orderNote ?: "", it.foodPrice)
                })

                cartViewModel.placeOrder(orderRequest)
            } else {
                Toast.makeText(requireContext(), "Your cart is empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeCartItems() {
        cartViewModel.allCartItems.observe(viewLifecycleOwner) {
            cartAdapter.setData(it)
        }
    }

    private fun observeTotalPrice() {
        cartViewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.tvSumTotal.text = it.toString()
        }
    }

    private fun observeOrderRequest() {
        cartViewModel.orderPlacedLiveData.observe(viewLifecycleOwner) {
            if (it.data == true) {
                val dialogFragment = PaymentSuccessDialog()
                dialogFragment.show(childFragmentManager, "PaymentSuccessDialog")
            }
        }
    }

    private fun observeLoading() {
        cartViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    @Suppress("DEPRECATION")
    private fun deliveryMethod() {
        binding.btTakeaway.setOnClickListener {
            binding.btTakeaway.setBackgroundColor(resources.getColor(R.color.light_magenta))
            binding.btTakeaway.setTextColor(resources.getColor(R.color.white))

            binding.btDelivery.setBackgroundColor(resources.getColor(R.color.light_grey))
            binding.btDelivery.setTextColor(resources.getColor(R.color.black))
        }

        binding.btDelivery.setOnClickListener {
            binding.btDelivery.setBackgroundColor(resources.getColor(R.color.light_magenta))
            binding.btDelivery.setTextColor(resources.getColor(R.color.white))

            binding.btTakeaway.setBackgroundColor(resources.getColor(R.color.light_grey))
            binding.btTakeaway.setTextColor(resources.getColor(R.color.black))
        }
    }

    @Suppress("DEPRECATION")
    private fun paymentMethod() {
        binding.btCash.setOnClickListener {
            binding.btCash.setBackgroundColor(resources.getColor(R.color.light_magenta))
            binding.btCash.setTextColor(resources.getColor(R.color.white))

            binding.btWallet.setBackgroundColor(resources.getColor(R.color.light_grey))
            binding.btWallet.setTextColor(resources.getColor(R.color.black))
        }

        binding.btWallet.setOnClickListener {
            binding.btWallet.setBackgroundColor(resources.getColor(R.color.light_magenta))
            binding.btWallet.setTextColor(resources.getColor(R.color.white))

            binding.btCash.setBackgroundColor(resources.getColor(R.color.light_grey))
            binding.btCash.setTextColor(resources.getColor(R.color.black))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}