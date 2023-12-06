package com.example.inventory.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inventory.R
import com.example.inventory.common.BaseActivity
import com.example.inventory.common.BaseFragment
import com.example.inventory.databinding.FragmentMainBinding
import com.example.inventory.main.bottomnavpages.*
import com.example.inventory.utils.ActivityUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment() {
    private lateinit var binding: FragmentMainBinding

    override fun getRoot(): View? {
        return binding.root
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BaseActivity?)?.showBackButton(true)

        setActionBarTitle(getString(R.string.app_main_title))

        setupBottomNavView()

    }

    private fun setupBottomNavView() {
        val bottomNavView = binding.bottomNavView
        bottomNavView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    ActivityUtils.replaceFragment(
                        childFragmentManager,
                        HomeFragment(),
                        R.id.subFragmentContainer
                    )
                    true
                }
                R.id.inventory -> {
                    ActivityUtils.replaceFragment(
                        childFragmentManager,
                        InventoryFragment(),
                        R.id.subFragmentContainer
                    )
                    true
                }
                R.id.events -> {
                    ActivityUtils.replaceFragment(
                        childFragmentManager,
                        EventsFragment(),
                        R.id.subFragmentContainer
                    )
                    true
                }
                R.id.settings -> {
                    ActivityUtils.replaceFragment(
                        childFragmentManager,
                        SettingsFragment(),
                        R.id.subFragmentContainer
                    )
                    true
                }
                R.id.player_lookup -> {
                    ActivityUtils.replaceFragment(
                        childFragmentManager,
                        PlayerLookupFragment(),
                        R.id.subFragmentContainer
                    )
                    true
                }
                else -> false
            }
        }
    }


}