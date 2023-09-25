package com.example.chall3.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chall3.model.Foods
import com.example.chall3.R
import com.example.chall3.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private var totalPrice: Int = 0
    private var currentAmount: Int = 1
    private var item: Foods? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        setData()
        seeOnMaps()
        setAddition()
        setReduction()
        //onBackPressed()
        iconBackClicked()

        return binding.root
    }

    private fun setData() {
        @Suppress("DEPRECATION")
        item = arguments?.getParcelable("item")

        item?.let {
            binding.ivImage.setImageResource(it.photo)
            binding.tvFoodPrice.text = item?.price.toString()
            binding.tvFoodName.text = item?.name
            binding.tvDesc.text = item?.description
            binding.btStar.text = item?.star
            binding.tvLocationDesc.text = item?.address
            binding.tvTotal.text = item?.price.toString()
        }
    }

    private fun setAddition() {
        binding.btPlus.setOnClickListener {
            currentAmount++
            updateTotalPrice()
        }

    }

    private fun setReduction() {
        binding.btMin.setOnClickListener {
            if (currentAmount > 1) {
                currentAmount--
                updateTotalPrice()
            } else {
                Toast.makeText(requireContext(), "Minimum purchase is 1", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateTotalPrice() {
        item?.let {
            totalPrice = item?.price?.times(currentAmount) ?: 0
            binding.tvNumber.text = currentAmount.toString()
            binding.tvTotal.text = totalPrice.toString()
        }
    }

    private fun seeOnMaps() {
        @Suppress("DEPRECATION")
        val item = arguments?.getParcelable<Foods>("item")

        binding.btMaps.setOnClickListener {
            val address = item?.address
            val map = "http://maps.google.co.in/maps?q=$address"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(map))
            startActivity(intent)
        }
    }

    /*private fun onBackPressed() {
        requireActivity()
            .onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                findNavController().navigate(R.id.homeFragment)
            }
    }*/

    private fun iconBackClicked() {
        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }
}