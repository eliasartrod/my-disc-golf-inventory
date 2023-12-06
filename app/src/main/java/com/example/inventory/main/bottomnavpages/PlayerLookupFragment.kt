package com.example.inventory.main.bottomnavpages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.inventory.R
import com.example.inventory.common.BaseActivity
import com.example.inventory.common.BaseFragment
import com.example.inventory.databinding.FragmentPlayerLookupBinding
import com.example.inventory.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerLookupFragment: BaseFragment() {
    private lateinit var binding: FragmentPlayerLookupBinding

    private val viewModel: MainViewModel by viewModels()

    override fun getRoot(): View? {
        return binding.root
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayerLookupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BaseActivity?)?.showBackButton(true)

        setActionBarTitle(getString(R.string.app_player_lookup_title))

    }
}