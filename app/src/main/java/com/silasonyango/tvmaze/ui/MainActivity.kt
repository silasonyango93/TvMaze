package com.silasonyango.tvmaze.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.silasonyango.tvmaze.R
import dagger.android.AndroidInjection

class MainActivity : AppCompatActivity() {
    private var mainNavController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)
//        val mainNavHostFragment =
//            supportFragmentManager.findFragmentById(R.id.root_nav_host_fragment) as? NavHostFragment
//        mainNavController = mainNavHostFragment?.navController
    }
}