package com.taetae98.qrreader.activity

import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.taetae98.modules.library.navigation.NavigationActivity
import com.taetae98.qrreader.R
import com.taetae98.qrreader.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : NavigationActivity<ActivityMainBinding>(
    R.layout.activity_main, R.id.nav_host
) {
    override val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(R.id.scanFragment, R.id.generateFragment, R.id.historyFragment, R.id.settingFragment)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateBottomNavigationView()
    }

    private fun onCreateBottomNavigationView() {
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}