package com.example.chall3

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
        }
    }

    private fun onBackPressed() {
        requireActivity()
            .onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                findNavController().navigate(R.id.homeFragment)
            }
    }
}