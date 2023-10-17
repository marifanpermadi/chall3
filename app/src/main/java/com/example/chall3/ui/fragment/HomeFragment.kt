package com.example.chall3.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chall3.R
import com.example.chall3.adapter.MenuCategoryAdapter
import com.example.chall3.adapter.MenuAdapter
import com.example.chall3.data.apimodel.DataCategory
import com.example.chall3.data.apimodel.DataMenu
import com.example.chall3.database.users.UserDatabase
import com.example.chall3.databinding.FragmentHomeBinding
import com.example.chall3.ui.SettingActivity
import com.example.chall3.utils.UserPreferences
import com.example.chall3.viewmodel.HomeViewModel
import com.example.chall3.viewmodel.MenuViewModel
import com.example.chall3.viewmodelfactory.HomeViewModelFactory

class HomeFragment : Fragment(), MenuAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var userPreferences: UserPreferences
    private lateinit var menuAdapter: MenuAdapter

    private val menuViewModel: MenuViewModel by viewModels {
        MenuViewModel.ViewModelFactory()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        userPreferences = UserPreferences(requireContext())

        val userDatabase = UserDatabase.getUserDataBase(requireContext())
        val userDao = userDatabase.userDao()
        homeViewModel = ViewModelProvider(requireActivity(), HomeViewModelFactory(userDao))[HomeViewModel::class.java]

        checkUser()

        homeViewModel.isListView.value = userPreferences.getLayoutPreferences()

        menuAdapter = MenuAdapter(listener = this)
        binding.rvVertical.setHasFixedSize(true)
        getListMenu()

        getMenuCategory()
        binding.rvHorizontal.setHasFixedSize(true)
        menuViewModel.menuCategory.observe(viewLifecycleOwner) {
            setCategory(it)
        }

        menuViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeViewModel.isListView.observe(viewLifecycleOwner) {
            toggleLayout()
        }

        showAllMenu()
        onBackPressed()
        settingActivity()

        return binding.root
    }

    private fun setCategory(listCategory: List<DataCategory>?) {
        binding.rvHorizontal.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        val categoryAdapter = listCategory?.let { dataCategories ->
            MenuCategoryAdapter(dataCategories) {
                getMenuByCategory(it.lowercase())
            }
        }
        binding.rvHorizontal.adapter = categoryAdapter
    }

    private fun getListMenu() {
        binding.rvVertical.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvVertical.adapter = menuAdapter

        showShimmerEffect()

        Handler(Looper.getMainLooper()).postDelayed({
            menuViewModel.getListMenu().observe(viewLifecycleOwner) { dataMenu ->
                if (dataMenu != null) {
                    menuAdapter.submitData(lifecycle, dataMenu)
                    hideShimmerEffect()
                }
            }
        }, DELAY)
    }

    private fun checkUser() {
        homeViewModel.getUserByEmail()
        homeViewModel.userLiveData.observe(viewLifecycleOwner) {
            binding.tvUsername.text = it.userName.uppercase()
        }
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

    private fun getMenuCategory() {
        menuViewModel.getMenuCategories()
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
        val bundle = bundleOf("item" to data)
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            showShimmerCategory()
        } else {
            hideShimmerCategory()
        }
    }

    companion object {
        const val DELAY = 2000L
    }


}