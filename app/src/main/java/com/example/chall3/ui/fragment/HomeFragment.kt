package com.example.chall3.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chall3.R
import com.example.chall3.adapter.HorizontalFoodAdapter
import com.example.chall3.adapter.MenuAdapter
import com.example.chall3.databinding.FragmentHomeBinding
import com.example.chall3.model.MenuCategory
import com.example.chall3.ui.SettingActivity
import com.example.chall3.utils.UserPreferences
import com.example.chall3.viewmodel.HomeViewModel
import com.example.chall3.viewmodel.MenuViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var userPreferences: UserPreferences
    private lateinit var menuAdapter: MenuAdapter

    private val menuViewModel: MenuViewModel by viewModels {
        MenuViewModel.ViewModelFactory()
    }

    private val listHorizontal = ArrayList<MenuCategory>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        userPreferences = UserPreferences(requireContext())

        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        homeViewModel.isListView.value = userPreferences.getLayoutPreferences()

        menuAdapter = MenuAdapter()
        binding.rvVertical.setHasFixedSize(true)
        getListMenu()

        binding.rvHorizontal.setHasFixedSize(true)
        if (listHorizontal.isEmpty()) {
            listHorizontal.addAll(getListFoodsHorizontal())
        }
        setupRecyclerListHorizontal()

        homeViewModel.isListView.observe(viewLifecycleOwner) {
            toggleLayout()
        }

        showAllMenu()
        onBackPressed()
        settingActivity()

        return binding.root
    }

    private fun getListMenu() {
        binding.rvVertical.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvVertical.adapter = menuAdapter

        showShimmerEffect()

        Handler(Looper.getMainLooper()).postDelayed({
            menuViewModel.getListMenu().observe(viewLifecycleOwner) { dataMenu ->
                if (dataMenu != null) {
                    hideShimmerEffect()
                    menuAdapter.submitData(lifecycle, dataMenu)
                }
            }
        }, DELAY)
    }

    private fun getMenuByCategory(category: String) {
        menuViewModel.getMenuByCategory(category).observe(viewLifecycleOwner) { dataMenu ->
            showShimmerEffect()
            if (dataMenu != null) {
                hideShimmerEffect()
                menuAdapter.submitData(lifecycle, dataMenu)
            }
        }
    }

    private fun showAllMenu() {
        binding.ivAll.setOnClickListener {
            binding.rvVertical.adapter = menuAdapter

            menuViewModel.getListMenu().observe(viewLifecycleOwner) { dataMenu ->
                if (dataMenu != null) {
                    menuAdapter.submitData(lifecycle, dataMenu)
                }
            }
        }
    }

    private fun getListFoodsHorizontal(): ArrayList<MenuCategory> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)

        val listFood = ArrayList<MenuCategory>()
        for (i in dataName.indices) {
            val food = MenuCategory(
                dataName[i],
                dataPhoto.getResourceId(i, -1)
            )
            listFood.add(food)
        }
        dataPhoto.recycle()

        return listFood
    }

    private fun setupRecyclerListHorizontal() {
        binding.rvHorizontal.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        val horizontalAdapter = HorizontalFoodAdapter(listHorizontal) {
            getMenuByCategory(it.lowercase())
            Log.d("Category clicked", it)
        }
        binding.rvHorizontal.adapter = horizontalAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun toggleRecyclerViewLayout(isListView: Boolean) {

        if (isListView) {
            showGrid()
        } else {
            showLinear()
        }

        binding.ivToggle.setImageResource(
            if (isListView) R.drawable.ic_list else R.drawable.ic_grid
        )
    }

    /*private fun itemClicked() {
        verticalFoodAdapter =
            VerticalFoodAdapter(listVertical, homeViewModel.isListView.value ?: true) { item ->
                val bundle = bundleOf("item" to item)
                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
            }
        binding.rvVertical.adapter = verticalFoodAdapter
    }*/

    private fun showGrid() {
        binding.rvVertical.layoutManager =
            GridLayoutManager(requireActivity(), 2)
        menuAdapter.isGridMode = true
        binding.rvVertical.adapter = menuAdapter
    }

    private fun showLinear() {
        binding.rvVertical.layoutManager =
            LinearLayoutManager(requireActivity())
        menuAdapter.isGridMode = false
        binding.rvVertical.adapter = menuAdapter
    }

    /* @SuppressLint("NotifyDataSetChanged")
     private fun updateRecyclerView(foodItems: ArrayList<Foods>) {

         verticalFoodAdapter.updateData(foodItems)
         verticalFoodAdapter.isGridMode = homeViewModel.isListView.value ?: true
         binding.rvVertical.adapter?.notifyDataSetChanged()

     }*/

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.shimmerFrameLayout.startShimmer()
        Log.d("Shimmer", "Shimmer executed")
        binding.rvVertical.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        Log.d("Shimmer", "Shimmer stopped")
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.rvVertical.visibility = View.VISIBLE
    }

    private fun toggleLayout() {
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
    }

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

    companion object {
        const val DELAY = 3000L
    }
}