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

import android.app.Activity
import android.content.Context
import android.net.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase


class MainFragment : Fragment() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private final var TAG = "MainActivity"

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)






        /**  BEGIN Check Internet Connection  */
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Log.d("Response","CODE  Android 9 Up")
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

            return if (isConnected) {
                //Connected
                inflater.inflate(R.layout.main_fragment, container, false)

            } else {
                //Not Connect Wifi/Cellular
                inflater.inflate(R.layout.no_internet, container, false)
            }

        }else{
            // Use Intent Filter for Android 8 and below
            Log.d("Response","CODE Below and  Android 8")
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

            return if (isConnected) {
                //Connected
                inflater.inflate(R.layout.main_fragment, container, false)

            } else {
                //Not Connect Wifi/Cellular
                inflater.inflate(R.layout.no_internet, container, false)

            }

        }
        /**  BEGIN Check Internet Connection  */
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**  BEGIN Firebase Analytic */
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            param(FirebaseAnalytics.Param.SCREEN_NAME,"Main")
        }
        /**  END Firebase Analytic */



        //====Action Bar Title======
        val actionBar = (activity as MainActivity).supportActionBar
        actionBar!!.setDisplayShowTitleEnabled(true)
        actionBar.title = ""









        //GOV Button
        view.findViewById<ImageButton>(R.id.button_government)?.setOnClickListener {
            val action = MainFragmentDirections.actionMainDestToFlowPlaceCategoryDest(
                    placeIdArgs = "01"
            )
            findNavController().navigate(action)
        }//Food Button
        view.findViewById<ImageButton>(R.id.button_food)?.setOnClickListener {
            val action = MainFragmentDirections.actionMainDestToFlowPlaceCategoryDest(
                    placeIdArgs = "03"
            )
            findNavController().navigate(action)
        }

        //Store Button
        view.findViewById<ImageButton>(R.id.button_store)?.setOnClickListener {
            val action = MainFragmentDirections.actionMainDestToFlowPlaceCategoryDest(
                    placeIdArgs = "04"
            )
            findNavController().navigate(action)
        }
        //Personal Button
        view.findViewById<ImageButton>(R.id.button_personal)?.setOnClickListener {
            val action = MainFragmentDirections.actionMainDestToFlowPlaceCategoryDest(
                    placeIdArgs = "05"
            )
            findNavController().navigate(action)
        }
        //Sport Button
        view.findViewById<ImageButton>(R.id.button_sport)?.setOnClickListener {
            val action = MainFragmentDirections.actionMainDestToFlowPlaceCategoryDest(
                    placeIdArgs = "06"
            )
            findNavController().navigate(action)
        }
        //Health Button
        view.findViewById<ImageButton>(R.id.button_education)?.setOnClickListener {
            val action = MainFragmentDirections.actionMainDestToFlowPlaceCategoryDest(
                    placeIdArgs = "07"
            )
            findNavController().navigate(action)
        }
        //Health Button
        view.findViewById<ImageButton>(R.id.button_health)?.setOnClickListener {
            val action = MainFragmentDirections.actionMainDestToFlowPlaceCategoryDest(
                    placeIdArgs = "08"
            )
            findNavController().navigate(action)
        }
        //HOTEL Button
        view.findViewById<ImageButton>(R.id.button_room)?.setOnClickListener {
            val action = MainFragmentDirections.actionMainDestToFlowPlaceCategoryDest(
                    placeIdArgs = "09"
            )
            findNavController().navigate(action)
        }

        //AUTO Button
        view.findViewById<ImageButton>(R.id.button_auto)?.setOnClickListener {
            val action = MainFragmentDirections.actionMainDestToFlowPlaceCategoryDest(
                    placeIdArgs = "10"
            )
            findNavController().navigate(action)
        }
        //Business Button
        view.findViewById<ImageButton>(R.id.button_business)?.setOnClickListener {
            val action = MainFragmentDirections.actionMainDestToFlowPlaceCategoryDest(
                    placeIdArgs = "11"
            )
            findNavController().navigate(action)
        }
        //SERVICES Button
        view.findViewById<ImageButton>(R.id.button_services)?.setOnClickListener {
            val action = MainFragmentDirections.actionMainDestToFlowPlaceCategoryDest(
                    placeIdArgs = "12"
            )
            findNavController().navigate(action)
        }
        //BANK Button
        view.findViewById<ImageButton>(R.id.button_bank)?.setOnClickListener {
            val action = MainFragmentDirections.actionMainDestToFlowPlaceCategoryDest(
                    placeIdArgs = "13"
            )
            findNavController().navigate(action)

        }
        //Religion Button
        view.findViewById<ImageButton>(R.id.button_religion)?.setOnClickListener {
            val action = MainFragmentDirections.actionMainDestToFlowPlaceCategoryDest(
                    placeIdArgs = "14"
            )
            findNavController().navigate(action)
        }
        //TRANSPORT Button
        view.findViewById<ImageButton>(R.id.button_transport)?.setOnClickListener {
            val action = MainFragmentDirections.actionMainDestToFlowPlaceCategoryDest(
                    placeIdArgs = "15"
            )
            findNavController().navigate(action)
        }
        //TRAVEL Button
        view.findViewById<ImageButton>(R.id.button_travel)?.setOnClickListener {
            val action = MainFragmentDirections.actionMainDestToFlowPlaceCategoryDest(
                    placeIdArgs = "16"
            )
            findNavController().navigate(action)
        }
        /**  END Navigate Send Argument Place ID   */


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    @Suppress("DEPRECATION")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

         if (isConnected) {
            //Connected
             if (id == R.id.searching) {

                 val action = MainFragmentDirections.actionMainDestToFlowPlaceSearchDest()
                 findNavController().navigate(action)
             }
             return super.onOptionsItemSelected(item)

        } else {
            //Not Connect Wifi/Cellular
             return super.onOptionsItemSelected(item)

        }

        //return super.onOptionsItemSelected(item)
    }
}
