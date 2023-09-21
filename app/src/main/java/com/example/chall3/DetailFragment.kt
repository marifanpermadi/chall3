package com.example.chall3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chall3.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        setData()
        seeOnMaps()
        onBackPressed()

        return binding.root
    }

    private fun setData() {
        @Suppress("DEPRECATION") val item = arguments?.getParcelable<Foods>("item")
        item?.let {
            binding.ivImage.setImageResource(it.photo)
            binding.tvFoodPrice.text = item.price.toString()
            binding.tvFoodName.text = item.name
            binding.tvDesc.text = item.description
            binding.btStar.text = item.star
            binding.tvLocationDesc.text = item.address
            binding.tvTotal.text = item.price.toString()
        }
    }

    private fun onBackPressed() {
        requireActivity()
            .onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                findNavController().navigate(R.id.homeFragment)
            }
    }

    private fun seeOnMaps() {
        @Suppress("DEPRECATION") val item = arguments?.getParcelable<Foods>("item")
        binding.btMaps.setOnClickListener {
            val address = item?.address
            val map = "http://maps.google.co.in/maps?q=$address"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(map))
            startActivity(intent)
        }
    }
}