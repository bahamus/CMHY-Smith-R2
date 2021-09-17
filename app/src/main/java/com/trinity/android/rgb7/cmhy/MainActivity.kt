/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trinity.android.rgb7.cmhy

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration : AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_activity)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        //*** Set up Action Bar***
        val navController = host.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBar(navController, appBarConfiguration)

        setupNavigationMenu(navController)

        // TODO Disable Create an AppBarConfiguration with the correct top-level destinations
//        val drawerLayout : DrawerLayout? = findViewById(R.id.drawer_layout)
//        appBarConfiguration = AppBarConfiguration(
//                setOf(R.id.main_dest),
//                drawerLayout)



        // TODO Disable Bottom Menu
       // setupBottomNavMenu(navController)

        // Test Logcat Check Destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }

            Log.d("NavigationActivity", "Navigated to $dest")
        }
    }

    // TODO Disable Bottom Nav Menu
//    private fun setupBottomNavMenu(navController: NavController) {
//        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
//        bottomNav?.setupWithNavController(navController)
//    }

    // TODO Use NavigationUI to set up a Navigation View
    private fun setupNavigationMenu(navController: NavController) {

        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
        sideNavView?.setupWithNavController(navController)

    }


    // TODO use Action Bar
    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {

        setupActionBarWithNavController(navController, appBarConfig)

    }

    // TODO Disqble Overflow Menu
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val retValue = super.onCreateOptionsMenu(menu)
//        val navigationView = findViewById<NavigationView>(R.id.nav_view)
//        if (navigationView == null) {
//            menuInflater.inflate(R.menu.overflow_menu, menu)
//            return true
//        }
//        return retValue
//    }

    // TODO Option Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return item.onNavDestinationSelected(findNavController(R.id.my_nav_host_fragment))
                || super.onOptionsItemSelected(item)

    }

    // TODO  NavigationUI
    override fun onSupportNavigateUp(): Boolean {

        return findNavController(R.id.my_nav_host_fragment).navigateUp(appBarConfiguration)
    }
}
