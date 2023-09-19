package com.example.chall3

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chall3.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

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
        listHorizontal.addAll(getListFoodsHorizontal())
        showRecyclerListHorizontal()

        binding.rvVertical.setHasFixedSize(true)
        listVertical.addAll(getListFoodsVertical())
        showRecyclerListVertical()

        val toggleImage = binding.ivToggle
        toggleImage.setOnClickListener {
            isListView = !isListView
            toggleRecyclerViewLayout()
            toggleImageViewImage(toggleImage)
        }

        val adapter = VerticalFoodAdapter(listVertical, isListView, onItemClick = {
            val fragment = DetailFragment.newInstance(it)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack(null)
                .commit()
        })
        /*adapter.onItemClick = { item ->

            val fragment = DetailFragment.newInstance(item)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack(null)
                .commit()
        }*/
        binding.rvVertical.adapter = adapter

        return binding.root
    }

    private fun getListFoodsHorizontal(): ArrayList<Foods> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataPrice = resources.getIntArray(R.array.data_price)
        val dataDesc = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)

        val listFood = ArrayList<Foods>()
        for (i in dataName.indices) {
            val food = Foods(dataName[i], dataPrice[i], dataPhoto.getResourceId(i, -1), dataDesc[i])
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

        val listFood = ArrayList<Foods>()
        for (i in dataName.indices) {
            val food = Foods(dataName[i], dataPrice[i], dataPhoto.getResourceId(i, -1), dataDesc[i])
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
        val listFoodAdapter = VerticalFoodAdapter(listVertical, isGridMode = true)
        binding.rvVertical.adapter = listFoodAdapter
    }

    private fun showLinear() {
        binding.rvVertical.layoutManager =
            LinearLayoutManager(requireActivity())
        val listFoodAdapter = VerticalFoodAdapter(listVertical, isGridMode = false)
        binding.rvVertical.adapter = listFoodAdapter
    }
}