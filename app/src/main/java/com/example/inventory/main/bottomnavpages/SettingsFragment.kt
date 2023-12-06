package com.example.inventory.main.bottomnavpages

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.inventory.R
import com.example.inventory.common.BaseActivity
import com.example.inventory.common.BaseFragment
import com.example.inventory.common.Event
import com.example.inventory.databinding.FragmentSettingsBinding
import com.example.inventory.login.LoginActivity
import com.example.inventory.login.LoginFragment
import com.example.inventory.main.MainViewModel
import com.example.inventory.utils.ActivityUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment: BaseFragment() {
    private lateinit var binding: FragmentSettingsBinding

    private val viewModel: MainViewModel by viewModels()

    private var sessionId: String? = null


    override fun getRoot(): View? {
        return binding.root
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BaseActivity?)?.showBackButton(true)

        setActionBarTitle(getString(R.string.app_settings_title))
        setupListeners()

        viewModel.snackBar.observe(viewLifecycleOwner) { showSnackBar(it) }
        viewModel.loading.observe(viewLifecycleOwner) { showLoadingProgress(it) }
        viewModel.isLogoutSuccessful.observe(viewLifecycleOwner) { setupLogOutListener(it) }

    }

    private fun setupListeners() {
        sessionId = viewModel.getSessionId()
        if (sessionId.isNullOrEmpty()) {
            binding.actionLogOut.text = getString(R.string.action_go_home)
            binding.actionLogOut.setOnClickListener {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)

                requireActivity().finish()
            }
        } else {
            binding.actionLogOut.text = getString(R.string.log_out)
            binding.actionLogOut.setOnClickListener {
                viewModel.logout()
            }
        }
    }

    private fun showLoadingProgress(event: Event<Boolean?>) {
        event.contentIfNotHandled?.let {
            if (it) {
                binding.progressIndicator.visibility = View.VISIBLE
            } else {
                binding.progressIndicator.visibility = View.GONE
            }
        }
    }

    private fun setupLogOutListener(event: Event<Boolean?>) {
        event.contentIfNotHandled?.let {
            if (it) {
                val fragment = LoginFragment()
                ActivityUtils.replaceFragment(
                    parentFragmentManager,
                    fragment,
                    R.id.fragment_container
                )
            }
        }
    }
}