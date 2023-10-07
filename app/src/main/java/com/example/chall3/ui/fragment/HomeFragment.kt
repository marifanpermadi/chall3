package com.example.chall3.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chall3.R
import com.example.chall3.adapter.HorizontalFoodAdapter
import com.example.chall3.adapter.MenuAdapter
import com.example.chall3.adapter.VerticalFoodAdapter
import com.example.chall3.databinding.FragmentHomeBinding
import com.example.chall3.model.Foods
import com.example.chall3.ui.SettingActivity
import com.example.chall3.utils.UserPreferences
import com.example.chall3.viewmodel.HomeViewModel
import com.example.chall3.viewmodel.MenuViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var userPreferences: UserPreferences
    private lateinit var menuAdapter: MenuAdapter

    private val menuViewModel: MenuViewModel by viewModels {
        MenuViewModel.ViewModelFactory()
    }

    private val listHorizontal = ArrayList<Foods>()
    private val listVertical = ArrayList<Foods>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        //userPreferences = UserPreferences(requireContext())

        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        //homeViewModel.isListView.value = userPreferences.getLayoutPreferences()

        menuAdapter = MenuAdapter ()

        menuViewModel.getListMenu().observe(viewLifecycleOwner) { dataMenu ->
            if (dataMenu != null) {
                menuAdapter.submitData(lifecycle, dataMenu)
            }
        }

        binding.rvVertical.setHasFixedSize(true)
        binding.rvVertical.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvVertical.adapter = menuAdapter




        binding.rvHorizontal.setHasFixedSize(true)
        if (listHorizontal.isEmpty()) {
            listHorizontal.addAll(getListFoodsHorizontal())
        }
        showRecyclerListHorizontal()

        /*homeViewModel.isListView.observe(viewLifecycleOwner) {
            toggleLayout()
        */

        /*homeViewModel.foodItems.observe(viewLifecycleOwner) { foodItems ->
            updateRecyclerView(foodItems)
        }*/

        onBackPressed()
        settingActivity()

        return binding.root
    }

    private fun getListMenu() {
        val adapter = MenuAdapter ()
        binding.rvVertical.adapter = adapter

        menuViewModel.getListMenu().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitData(lifecycle, it)
            }
        }
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

    /*private fun showRecyclerListVertical() {
        binding.rvVertical.layoutManager =
            GridLayoutManager(requireActivity(), 2)
        val listFoodAdapter = VerticalFoodAdapter(listVertical)
        binding.rvVertical.adapter = listFoodAdapter
    }*/

    @SuppressLint("NotifyDataSetChanged")
    /*private fun toggleRecyclerViewLayout(isListView: Boolean) {

        if (isListView) {
            showGrid()
        } else {
            showLinear()
        }

        binding.ivToggle.setImageResource(
            if (isListView) R.drawable.ic_list else R.drawable.ic_grid
        )
    }*/

    /*private fun itemClicked() {
        verticalFoodAdapter =
            VerticalFoodAdapter(listVertical, homeViewModel.isListView.value ?: true) { item ->
                val bundle = bundleOf("item" to item)
                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
            }
        binding.rvVertical.adapter = verticalFoodAdapter
    }*/

    /*private fun showGrid() {
        binding.rvVertical.layoutManager =
            GridLayoutManager(requireActivity(), 2)
        verticalFoodAdapter.isGridMode = true
        binding.rvVertical.adapter = verticalFoodAdapter
    }*/

    /*private fun showLinear() {
        binding.rvVertical.layoutManager =
            LinearLayoutManager(requireActivity())
        verticalFoodAdapter.isGridMode = false
        binding.rvVertical.adapter = verticalFoodAdapter
    }*/

   /* @SuppressLint("NotifyDataSetChanged")
    private fun updateRecyclerView(foodItems: ArrayList<Foods>) {

        verticalFoodAdapter.updateData(foodItems)
        verticalFoodAdapter.isGridMode = homeViewModel.isListView.value ?: true
        binding.rvVertical.adapter?.notifyDataSetChanged()

    }*/

   /* private fun toggleLayout() {
        val toggleImage = binding.ivToggle
        val currentLayoutValue =
            homeViewModel.isListView.value ?: userPreferences.getLayoutPreferences()

        toggleRecyclerViewLayout(currentLayoutValue)
        toggleImage.setImageResource(if (currentLayoutValue) R.drawable.ic_list else R.drawable.ic_grid)

        toggleImage.setOnClickListener {
            val newListViewValue = !currentLayoutValue
            homeViewModel.isListView.value = newListViewValue
            userPreferences.saveLayoutPreferences(newListViewValue)
        }
    }*/

    private fun settingActivity() {
        binding.ivSetting.setOnClickListener {
            val intent = Intent(this@HomeFragment.requireContext(), SettingActivity::class.java)
            startActivity(intent)
        }
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