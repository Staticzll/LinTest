package com.tianjuan.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.tianjuan.navigation.databinding.NavigationActivityBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = NavigationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val appBarConfiguration = AppBarConfiguration(
//            setOf(R.id.home_dest, R.id.home_deeplink_dest),
//            binding.drawerLayout
//        )
//
//        val navController = findNavController(R.id.my_nav_host_fragment)
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}