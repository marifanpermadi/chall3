package com.example.chall3.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chall3.model.Foods
import com.example.chall3.adapter.HorizontalFoodAdapter
import com.example.chall3.R
import com.example.chall3.adapter.VerticalFoodAdapter
import com.example.chall3.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var verticalFoodAdapter: VerticalFoodAdapter

    private val listHorizontal = ArrayList<Foods>()
    private val listVertical = ArrayList<Foods>()

    private var isListView = true
    private val listLayout = arrayOf(
        R.drawable.ic_list,
        R.drawable.ic_grid
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.rvHorizontal.setHasFixedSize(true)
        if (listHorizontal.isEmpty()) {
            listHorizontal.addAll(getListFoodsHorizontal())
        }
        showRecyclerListHorizontal()

        binding.rvVertical.setHasFixedSize(true)
        if (listVertical.isEmpty()) {
            listVertical.addAll(getListFoodsVertical())
        }
        showRecyclerListVertical()

        toggleLayout()
        itemClicked()
        onBackPressed()

        return binding.root
    }

    private fun getListFoodsHorizontal(): ArrayList<Foods> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataPrice = resources.getIntArray(R.array.data_price)
        val dataDesc = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataStar = resources.getStringArray(R.array.data_star)
        val dataAddress = resources.getStringArray(R.array.data_address)

        val listFood = ArrayList<Foods>()
        for (i in dataName.indices) {
            val food = Foods(
                dataName[i],
                dataPrice[i],
                dataPhoto.getResourceId(i, -1),
                dataDesc[i],
                dataStar[i],
                dataAddress[i]
            )
            listFood.add(food)
        }

        dataPhoto.recycle()

        return listFood
    }

    private fun showRecyclerListHorizontal() {
        binding.rvHorizontal.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        val listFoodAdapter = HorizontalFoodAdapter(listHorizontal)
        binding.rvHorizontal.adapter = listFoodAdapter
    }

    private fun getListFoodsVertical(): ArrayList<Foods> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataPrice = resources.getIntArray(R.array.data_price)
        val dataDesc = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataStar = resources.getStringArray(R.array.data_star)
        val dataAddress = resources.getStringArray(R.array.data_address)

        val listFood = ArrayList<Foods>()
        for (i in dataName.indices) {
            val food = Foods(
                dataName[i],
                dataPrice[i],
                dataPhoto.getResourceId(i, -1),
                dataDesc[i],
                dataStar[i],
                dataAddress[i]
            )
            listFood.add(food)
        }

        dataPhoto.recycle()

        return listFood
    }

    private fun showRecyclerListVertical() {
        binding.rvVertical.layoutManager =
            GridLayoutManager(requireActivity(), 2)
        val listFoodAdapter = VerticalFoodAdapter(listVertical)
        binding.rvVertical.adapter = listFoodAdapter
    }

    private fun toggleImageViewImage(imageView: ImageView) {
        imageView.setImageResource(listLayout[if (isListView) 0 else 1])
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun toggleRecyclerViewLayout() {
        isListView = !isListView

        if (isListView) {
            showGrid()
        } else {
            showLinear()
        }

        binding.rvVertical.adapter?.notifyDataSetChanged()
    }

    private fun showGrid() {
        binding.rvVertical.layoutManager =
            GridLayoutManager(requireActivity(), 2)
        verticalFoodAdapter.isGridMode = true
        binding.rvVertical.adapter = verticalFoodAdapter
    }

    private fun showLinear() {
        binding.rvVertical.layoutManager =
            LinearLayoutManager(requireActivity())
        verticalFoodAdapter.isGridMode = false
        binding.rvVertical.adapter = verticalFoodAdapter
    }

    private fun toggleLayout() {
        val toggleImage = binding.ivToggle
        toggleImage.setOnClickListener {
            toggleRecyclerViewLayout()
            toggleImageViewImage(toggleImage)
        }
    }

    private fun itemClicked() {

        val navController = findNavController()
        val onItemClick: (Foods) -> Unit = { item ->
            val bundle = bundleOf("item" to item)
            navController.navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }

        verticalFoodAdapter = VerticalFoodAdapter(listVertical, isListView, onItemClick)
        binding.rvVertical.adapter = verticalFoodAdapter
    }

    private fun onBackPressed() {
        val navController = findNavController()
        requireActivity()
            .onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                if (navController.currentDestination?.id == R.id.homeFragment) {
                    requireActivity().finish()
                } else {
                    navController.navigateUp()
                }
            }
    }

}