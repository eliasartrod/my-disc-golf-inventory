package com.example.inventory.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.inventory.R
import com.example.inventory.common.BaseActivity
import com.example.inventory.common.BaseFragment
import com.example.inventory.databinding.FragmentLogInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: BaseFragment() {
    private lateinit var binding: FragmentLogInBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun getRoot(): View? {
        return binding.root
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BaseActivity?)?.showBackButton(false)
        setActionBarTitle(getString(R.string.app_home_title))

        viewModel.snackBar.observe(viewLifecycleOwner) { showSnackBar(it) }

        binding.actionLogIn.setOnClickListener {
            viewModel.sendAuthentication("eliasartrod12", "Tangaaa05!")
        }

        binding.actionLogOut.setOnClickListener {
            viewModel.logout()
        }

    }

}