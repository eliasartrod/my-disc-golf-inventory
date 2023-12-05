package com.example.inventory.main

import android.os.Bundle
import com.example.inventory.R
import com.example.inventory.common.BaseActivity
import com.example.inventory.login.LoginFragment
import com.example.inventory.utils.ActivityUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        val fragment = MainFragment()
                ActivityUtils.addFragment(
                    supportFragmentManager,
                    fragment,
                    R.id.fragment_container
                )
    }
}