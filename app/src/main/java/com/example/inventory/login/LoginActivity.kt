package com.example.inventory.login

import android.os.Bundle
import com.example.inventory.R
import com.example.inventory.common.BaseActivity
import com.example.inventory.utils.ActivityUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        val fragment = LoginFragment()
        ActivityUtils.addFragment(
            supportFragmentManager,
            fragment,
            R.id.fragment_container
        )
    }
}