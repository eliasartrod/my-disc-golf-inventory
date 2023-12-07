package com.example.inventory.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.inventory.R
import com.example.inventory.common.BaseActivity
import com.example.inventory.common.BaseFragment
import com.example.inventory.common.Event
import com.example.inventory.common.SnackBarMessage
import com.example.inventory.databinding.FragmentLogInBinding
import com.example.inventory.main.MainFragment
import com.example.inventory.utils.ActivityUtils
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

        setupListeners()

        viewModel.snackBar.observe(viewLifecycleOwner) { showSnackBar(it) }
        viewModel.loading.observe(viewLifecycleOwner) { showLoadingProgress(it) }
        viewModel.isLogInSuccessful.observe(viewLifecycleOwner) { setupLogInListener(it) }

    }

    private fun setupListeners() {
        binding.actionClearCache.setOnClickListener {
            viewModel.invalidatePreferences()
        }
        binding.actionContinueAsGuest.setOnClickListener {
            viewModel.invalidatePreferences()
            val fragment = MainFragment()
            ActivityUtils.addFragmentWithBackStack(
                parentFragmentManager,
                fragment,
                R.id.fragment_container,
                "main_fragment"
            )
        }
        // Set up TextWatcher for username EditText
        binding.userName.addTextChangedListener(createTextWatcher())

        // Set up TextWatcher for userPassword EditText
        binding.userPassword.addTextChangedListener(createTextWatcher())

    }

    private fun createTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed in this case
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed in this case
            }

            override fun afterTextChanged(s: Editable?) {
                // Update button states when any text changes
                updateButtonState()
            }
        }
    }

    private fun updateButtonState() {
        val usernameEmpty = binding.userName.text.isNullOrEmpty()
        val passwordEmpty = binding.userPassword.text.isNullOrEmpty()

        // Disable buttons if any of the fields are empty or invalid
        val isButtonEnabled = !(usernameEmpty || passwordEmpty)
        binding.actionLogIn.isEnabled = isButtonEnabled
        binding.actionClear.isEnabled = isButtonEnabled

        if (isButtonEnabled) {
            binding.actionLogIn.setOnClickListener {
                val username = binding.userName.text.toString()
                val password = binding.userPassword.text.toString()

                viewModel.sendAuthentication(username, password)
                clearFields()
            }
            binding.actionClear.setOnClickListener {
                clearFields()
            }
        }

    }

    private fun clearFields() {
        binding.userName.text = null
        binding.userPassword.text = null
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

    private fun setupLogInListener(event: Event<Boolean?>) {
        event.contentIfNotHandled?.let {
            if (it) {
                val fragment = MainFragment()
                ActivityUtils.addFragmentWithBackStack(
                    parentFragmentManager,
                    fragment,
                    R.id.fragment_container,
                    "main_fragment"
                )
            }
        }
    }

}