package com.example.chall3.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chall3.R
import com.example.chall3.adapter.CategoryAdapterDI
import com.example.chall3.adapter.MenuAdapterDI
import com.example.chall3.data.apimodel.DataMenu
import com.example.chall3.databinding.FragmentHomeBinding
import com.example.chall3.ui.SettingActivity
import com.example.chall3.utils.NetworkResult
import com.example.chall3.utils.UserPreferences
import com.example.chall3.viewmodel.HomeViewModel
import com.example.chall3.viewmodel.MenuViewModelDI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(),
    MenuAdapterDI.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var userPreferences: UserPreferences

    private lateinit var menuAdapterDI: MenuAdapterDI
    private lateinit var categoryAdapterDI: CategoryAdapterDI

    private val menuViewModelDI: MenuViewModelDI by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        userPreferences = UserPreferences(requireContext())

        checkUser()

        homeViewModel.isListView.value = userPreferences.getLayoutPreferences()

        homeViewModel.isListView.observe(viewLifecycleOwner) {
            toggleLayout()
        }

        categoryAdapterDI = CategoryAdapterDI { requestMenuByCategory(it.lowercase()) }
        menuAdapterDI = MenuAdapterDI(listener = this)

        binding.rvVertical.setHasFixedSize(true)
        binding.rvHorizontal.setHasFixedSize(true)

        setupRecyclerView()
        readDataMenu()
        readDataCategory()
        showAllMenu()
        onBackPressed()
        settingActivity()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvVertical.adapter = menuAdapterDI
        binding.rvHorizontal.adapter = categoryAdapterDI

        binding.rvVertical.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvHorizontal.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        showShimmerEffect()
        showShimmerCategory()
    }

    private fun readDataCategory() {
        Log.d("Read database", "read category database called")
        lifecycleScope.launch {
            menuViewModelDI.readCategory.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    categoryAdapterDI.setData(database.first().categoryResponse)
                    hideShimmerCategory()
                } else {
                    requestCategoryApi()
                }
            }
        }
    }

    private fun requestCategoryApi() {
        Log.d("Call api", "category api called")
        menuViewModelDI.getCategory()
        menuViewModelDI.categoryResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    Log.d("Call success", "api called successfully")
                    hideShimmerCategory()
                    response.data?.let { categoryAdapterDI.setData(it) }
                }

                is NetworkResult.Error -> {
                    Log.d("Call success", "api call failed")
                    hideShimmerCategory()
                    loadCategoryFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerCategory()
                }
            }
        }

    }

    private fun loadCategoryFromCache() {
        menuViewModelDI.readCategory.observe(viewLifecycleOwner) { database ->
            if (database.isNotEmpty()) {
                categoryAdapterDI.setData(database.first().categoryResponse)
            }
        }
    }

    private fun readDataMenu() {
        Log.d("Read database", "read menu database called")
        lifecycleScope.launch {
            menuViewModelDI.readMenu.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    menuAdapterDI.setData(database.first().listMenuResponse)
                    hideShimmerEffect()
                } else {
                    requestMenuFromApi()
                }
            }
        }
    }

    private fun requestMenuFromApi() {
        Log.d("Call api", "menu api called")
        menuViewModelDI.getListMenu()
        menuViewModelDI.listMenuResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { menuAdapterDI.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadMenuFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun loadMenuFromCache() {
        menuViewModelDI.readMenu.observe(viewLifecycleOwner) { database ->
            if (database.isNotEmpty()) {
                menuAdapterDI.setData(database.first().listMenuResponse)
            }
        }
    }

    private fun requestMenuByCategory(category: String) {
        Log.d("Call api", "menu by category api called")
        showShimmerEffect()
        menuViewModelDI.getMenuByCategory(category)
        menuViewModelDI.listMenuByCategoryResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { menuAdapterDI.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadMenuFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun showAllMenu() {
        binding.ivAll.setOnClickListener {
            menuAdapterDI.clearData()
            readDataMenu()
        }
    }

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

    private fun showGrid() {
        binding.rvVertical.layoutManager =
            GridLayoutManager(requireActivity(), 2)
        menuAdapterDI.isGridMode = true
        binding.rvVertical.adapter = menuAdapterDI
    }

    private fun showLinear() {
        binding.rvVertical.layoutManager =
            LinearLayoutManager(requireActivity())
        menuAdapterDI.isGridMode = false
        binding.rvVertical.adapter = menuAdapterDI
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.shimmerFrameLayout.startShimmer()
        binding.rvVertical.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.rvVertical.visibility = View.VISIBLE
    }

    private fun showShimmerCategory() {
        binding.shimmerCategory.visibility = View.VISIBLE
        binding.shimmerCategory.startShimmer()
        binding.rvHorizontal.visibility = View.GONE
    }

    private fun hideShimmerCategory() {
        binding.shimmerCategory.stopShimmer()
        binding.shimmerCategory.visibility = View.GONE
        binding.rvHorizontal.visibility = View.VISIBLE
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

    override fun onItemClick(data: DataMenu) {
        Log.d("Item clicked", "Menu Item clicked")
        val bundle = bundleOf("item" to data)
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }

    private fun checkUser() {
        homeViewModel.getUserByEmail()
        homeViewModel.userLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.tvUsername.text = it.userName.uppercase()
            } else {
                binding.tvUsername.text = "Binarian"
            }
        }
    }

}