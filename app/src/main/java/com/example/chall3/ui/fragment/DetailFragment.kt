package com.example.chall3.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.chall3.R
import com.example.chall3.data.apimodel.DataMenu
import com.example.chall3.databinding.FragmentDetailBinding
import com.example.chall3.viewmodel.DetailViewModel
import com.example.chall3.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val homeViewModel: HomeViewModel by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()

    private var item: DataMenu? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        homeViewModel.isListView.value = true

        observeTotalPrice()
        observeCurrentAmount()
        observeNote()

        setData()
        seeOnMaps()
        setAddition()
        setReduction()
        iconBackClicked()
        takeNote()
        addToCart()

        return binding.root
    }

    private fun observeNote() {
        detailViewModel.orderNote.observe(viewLifecycleOwner) { }
    }

    private fun observeCurrentAmount() {
        detailViewModel.currentAmount.observe(viewLifecycleOwner) { newAmount ->
            binding.tvNumber.text = newAmount.toString()
        }
    }

    private fun observeTotalPrice() {
        detailViewModel.totalPrice.observe(viewLifecycleOwner) { newTotalPrice ->
            binding.tvTotal.text = newTotalPrice.toString()
        }
    }

    private fun setData() {
        @Suppress("DEPRECATION")
        item = arguments?.getParcelable("item")

        item?.let {

            val image = binding.ivImage

            Glide.with(requireContext())
                .load(it.imageUrl)
                .into(image)

            binding.tvFoodPrice.text = item?.harga.toString()
            binding.tvFoodName.text = item?.nama
            binding.tvDesc.text = item?.detail
            binding.tvLocationDesc.text = item?.alamatResto
            binding.tvTotal.text = item?.harga.toString()

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
        val item = arguments?.getParcelable<DataMenu>("item")

        binding.btMaps.setOnClickListener {
            val address = item?.alamatResto
            val map = "http://maps.google.co.in/maps?q=$address"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(map))
            startActivity(intent)
        }
    }

    @Suppress("DEPRECATION")
    private fun iconBackClicked() {
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun addToCart() {
        binding.btCart.setOnClickListener {
            detailViewModel.addToCart()

            showItemAddedSnackBar()
            fromTheStart()
        }
    }

    private fun takeNote() {
        binding.ivCheck.setOnClickListener {
            val note = binding.etNote.text.toString()
            detailViewModel.setOrderNote(note)
            Toast.makeText(requireContext(), "Note added", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("InflateParams")
    private fun showItemAddedSnackBar() {
        val inflater = LayoutInflater.from(requireContext())
        val customView = inflater.inflate(R.layout.item_added_snackbar, null)

        val snackBar = Snackbar.make(binding.btCart, "", Snackbar.LENGTH_SHORT)
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

        snackBarLayout.removeAllViews()
        snackBarLayout.addView(customView, 0)

        customView.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToCartFragment()
            findNavController().navigate(action)
            snackBar.dismiss()
        }

        snackBar.show()
    }

    private fun fromTheStart() {
        binding.etNote.text?.clear()

        detailViewModel.setCurrentAmount(1)
        item?.let { detailViewModel.clearTotalPrice(it.harga) }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        detailViewModel.setCurrentAmount(1)
        item?.let { detailViewModel.clearTotalPrice(it.harga) }
    }
}