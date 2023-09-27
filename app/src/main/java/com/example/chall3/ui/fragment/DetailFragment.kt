package com.example.chall3.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.chall3.databinding.FragmentDetailBinding
import com.example.chall3.model.Foods
import com.example.chall3.viewmodel.DetailViewModel
import com.example.chall3.viewmodel.HomeViewModel


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var detailViewModel: DetailViewModel

    private var item: Foods? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]

        homeViewModel.isListView.value = true

        detailViewModel.currentAmount.observe(viewLifecycleOwner) { newAmount ->
            binding.tvNumber.text = newAmount.toString()
        }

        detailViewModel.totalPrice.observe(viewLifecycleOwner) { newTotalPrice ->
            binding.tvTotal.text = newTotalPrice.toString()
        }

        setData()
        seeOnMaps()
        setAddition()
        setReduction()
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

            detailViewModel.initSelectedItem(it)
        }
    }

    private fun setAddition() {
        binding.btPlus.setOnClickListener {
            val newAmount = detailViewModel.currentAmount.value?.plus(1) ?: 1
            detailViewModel.setCurrentAmount(newAmount)

        }
    }

    private fun setReduction() {
        binding.btMin.setOnClickListener {
            val currentAmount = detailViewModel.currentAmount.value ?: 1
            if (currentAmount > 1) {
                val newAmount = currentAmount - 1
                detailViewModel.setCurrentAmount(newAmount)
            } else {
                Toast.makeText(requireContext(), "Minimum purchase is 1", Toast.LENGTH_SHORT).show()
            }
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

    private fun iconBackClicked() {
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        detailViewModel.setCurrentAmount(1)
        item?.let { detailViewModel.clearTotalPrice(it.price) }
    }
}