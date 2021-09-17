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

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import java.util.*

/**
 * Presents how multiple steps flow could be implemented.
 */
class PlaceCategoryFragment : Fragment() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.place_category_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //**Receive Argument from Main Fragment**
        val safeArgs: PlaceCategoryFragmentArgs by navArgs()
        val placeIdCat = safeArgs.placeIdArgs


        /**  BEGIN Firebase Analytic */
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            param(FirebaseAnalytics.Param.SCREEN_NAME,"Place_Category")
        }


        //Place Category ID
        //Make Name for Call String value
        val catIdPlusName = "main_cat_$placeIdCat"
        val analyticPlaceCatName : String = getString(resources.getIdentifier(catIdPlusName, "string",context?.packageName))

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT){
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "CatID")
            param(FirebaseAnalytics.Param.ITEM_ID,analyticPlaceCatName)
        }
        /**  END Firebase Analytic */


        /**  BEGIN Admob */
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(context)

        view.findViewById<AdView>(R.id.adViewBanner).apply {

            val testDeviceIds = listOf("407ABC6C56886BFC982CA879C25A4138")
            val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
            MobileAds.setRequestConfiguration(configuration)
//            val location = Location("AdMobProvider")
//            location.latitude = 18.790218528898254
//            location.latitude = 98.98770239881131
//            val adBuilder = AdRequest.Builder()
//            adBuilder.setLocation(location)
//            adBuilder.addKeyword("เชียงใหม่");

            val adRequest = AdRequest.Builder().build()
            loadAd(adRequest)


        }

        /**  END Admob */



        //====Action Bar Title======
        /**  BEGIN Action Bar Tile Text */
        val actionBar = (activity as MainActivity).supportActionBar
        actionBar!!.setDisplayShowTitleEnabled(true)
        actionBar.title = placeNameSelect(placeIdCat)
        //actionBar.setIcon(null)

        /**  END  Action Bar Tile Text */

        /**  BEGIN Check&Load String-Array Category */
        //val placeID = "13"
        //Condition Check Category ID
        val itemList: Array<String> = when (placeIdCat) {

            "01" -> {
                resources.getStringArray(R.array.cat_government_id)
            }
            "03" -> {
                resources.getStringArray(R.array.cat_food_id)
            }
            "04" -> {
                resources.getStringArray(R.array.cat_shop_id)
            }
            "05" -> {
                resources.getStringArray(R.array.cat_personal_id)
            }
            "06" -> {
                resources.getStringArray(R.array.cat_sport_id)
            }
            "07" -> {
                resources.getStringArray(R.array.cat_education_id)
            }
            "08" -> {
                resources.getStringArray(R.array.cat_health_id)
            }
            "09" -> {
                resources.getStringArray(R.array.cat_room_id)
            }
            "10" -> {
                resources.getStringArray(R.array.cat_auto_id)
            }
            "11" -> {
                resources.getStringArray(R.array.cat_business_id)
            }
            "12" -> {
                resources.getStringArray(R.array.cat_services_id)
            }
            "13" -> {
                resources.getStringArray(R.array.cat_finance_id)
            }
            "14" -> {
                resources.getStringArray(R.array.cat_religion_id)
            }
            "15" -> {
                resources.getStringArray(R.array.cat_transport_id)
            }
            "16" -> {
                resources.getStringArray(R.array.cat_travel_id)
            }
            else -> {
                arrayOf("0000")
            }
        }
        view.findViewById<GridView>(R.id.grid_view).apply {
            adapter = CategoryCustomAdapter(context, itemList, placeIdCat) //This Nee to by Mycustom Adapter

        }
        /**  END Navigate Send Argument Place ID   */


    }

    /**  BEGIN Image Adapter  */
    class CategoryCustomAdapter(
            context: Context, private val names: Array<String>, private val catId: String
    ) : BaseAdapter() {
        val mContext: Context = context


        //Response how many row in my list
        override fun getCount(): Int {
            return names.size
        }

        //You can also ignore this
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        //ignore for now
        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }


        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {


            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain =
                    layoutInflater.inflate(R.layout.list_item_place_category, viewGroup, false)

            //Images Icon & Click
            val namesImagePos = names[position]
            rowMain.findViewById<ImageView>(R.id.icon).apply {
                setImageResource(
                        resources.getIdentifier(
                                "btn_category_$namesImagePos",
                                "drawable",
                                context.packageName
                        )
                )
                //Check Click and Send ID to Place List
                setOnClickListener {
                    val action = PlaceCategoryFragmentDirections.actionFlowPlaceCategoryDestToPlaceListFragment(namesImagePos)
                    findNavController().navigate(action)

                }
            }

            //Text Under Icon  user Array
            rowMain.findViewById<TextView>(R.id.textView).apply {

                when (catId) {
                    "01" -> {
                        val nameArray: Array<String> =
                                resources.getStringArray(R.array.cat_government_name)
                        text = nameArray[position]
                        setOnClickListener {
                            val action = PlaceCategoryFragmentDirections.actionFlowPlaceCategoryDestToPlaceListFragment(namesImagePos)
                            findNavController().navigate(action)

                        }
                    }
                    "03" -> {
                        val nameArray: Array<String> =
                                resources.getStringArray(R.array.cat_food_name)
                        text = nameArray[position]
                        setOnClickListener {
                            val action = PlaceCategoryFragmentDirections.actionFlowPlaceCategoryDestToPlaceListFragment(namesImagePos)
                            findNavController().navigate(action)

                        }
                    }
                    "04" -> {
                        val nameArray: Array<String> =
                                resources.getStringArray(R.array.cat_shop_name)
                        text = nameArray[position]
                        setOnClickListener {
                            val action = PlaceCategoryFragmentDirections.actionFlowPlaceCategoryDestToPlaceListFragment(namesImagePos)
                            findNavController().navigate(action)

                        }
                    }
                    "05" -> {
                        val nameArray: Array<String> =
                                resources.getStringArray(R.array.cat_personal_name)
                        text = nameArray[position]
                        setOnClickListener {
                            val action = PlaceCategoryFragmentDirections.actionFlowPlaceCategoryDestToPlaceListFragment(namesImagePos)
                            findNavController().navigate(action)

                        }
                    }
                    "06" -> {
                        val nameArray: Array<String> =
                                resources.getStringArray(R.array.cat_sport_name)
                        text = nameArray[position]
                        setOnClickListener {
                            val action = PlaceCategoryFragmentDirections.actionFlowPlaceCategoryDestToPlaceListFragment(namesImagePos)
                            findNavController().navigate(action)

                        }
                    }
                    "07" -> {
                        val nameArray: Array<String> =
                                resources.getStringArray(R.array.cat_education_name)
                        text = nameArray[position]
                        setOnClickListener {
                            val action = PlaceCategoryFragmentDirections.actionFlowPlaceCategoryDestToPlaceListFragment(namesImagePos)
                            findNavController().navigate(action)

                        }
                    }
                    "08" -> {
                        val nameArray: Array<String> =
                                resources.getStringArray(R.array.cat_health_name)
                        text = nameArray[position]
                        setOnClickListener {
                            val action = PlaceCategoryFragmentDirections.actionFlowPlaceCategoryDestToPlaceListFragment(namesImagePos)
                            findNavController().navigate(action)

                        }
                    }
                    "09" -> {
                        val nameArray: Array<String> =
                                resources.getStringArray(R.array.cat_room_name)
                        text = nameArray[position]
                        setOnClickListener {
                            val action = PlaceCategoryFragmentDirections.actionFlowPlaceCategoryDestToPlaceListFragment(namesImagePos)
                            findNavController().navigate(action)

                        }
                    }
                    "10" -> {
                        val nameArray: Array<String> =
                                resources.getStringArray(R.array.cat_auto_name)
                        text = nameArray[position]
                        setOnClickListener {
                            val action = PlaceCategoryFragmentDirections.actionFlowPlaceCategoryDestToPlaceListFragment(namesImagePos)
                            findNavController().navigate(action)

                        }
                    }
                    "11" -> {
                        val nameArray: Array<String> =
                                resources.getStringArray(R.array.cat_business_name)
                        text = nameArray[position]
                        setOnClickListener {
                            val action = PlaceCategoryFragmentDirections.actionFlowPlaceCategoryDestToPlaceListFragment(namesImagePos)
                            findNavController().navigate(action)

                        }
                    }
                    "12" -> {
                        val nameArray: Array<String> =
                                resources.getStringArray(R.array.cat_service_name)
                        text = nameArray[position]
                        setOnClickListener {
                            val action = PlaceCategoryFragmentDirections.actionFlowPlaceCategoryDestToPlaceListFragment(namesImagePos)
                            findNavController().navigate(action)

                        }
                    }
                    "13" -> {
                        val nameArray: Array<String> =
                                resources.getStringArray(R.array.cat_finance_name)
                        text = nameArray[position]
                        setOnClickListener {
                            val action = PlaceCategoryFragmentDirections.actionFlowPlaceCategoryDestToPlaceListFragment(namesImagePos)
                            findNavController().navigate(action)

                        }
                    }
                    "14" -> {
                        val nameArray: Array<String> =
                                resources.getStringArray(R.array.cat_religion_name)
                        text = nameArray[position]
                        setOnClickListener {
                            val action = PlaceCategoryFragmentDirections.actionFlowPlaceCategoryDestToPlaceListFragment(namesImagePos)
                            findNavController().navigate(action)

                        }
                    }
                    "15" -> {
                        val nameArray: Array<String> =
                                resources.getStringArray(R.array.cat_transport_name)
                        text = nameArray[position]
                        setOnClickListener {
                            val action = PlaceCategoryFragmentDirections.actionFlowPlaceCategoryDestToPlaceListFragment(namesImagePos)
                            findNavController().navigate(action)

                        }
                    }
                    "16" -> {
                        val nameArray: Array<String> =
                                resources.getStringArray(R.array.cat_travel_name)
                        text = nameArray[position]
                        setOnClickListener {
                            val action = PlaceCategoryFragmentDirections.actionFlowPlaceCategoryDestToPlaceListFragment(namesImagePos)
                            findNavController().navigate(action)

                        }
                    }
                }

            }
            return rowMain
        }

    }

    /**  End Image Adapter  */


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    //Todo Option Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.searching) {

            val action = PlaceCategoryFragmentDirections.actionFlowPlaceCategoryDestToFlowPlaceSearchDest()
            findNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }

    //Todo Select Name
    private fun placeNameSelect(placeIdCat: String): String {

        when (placeIdCat) {

            "01" -> {
                return getString(R.string.main_cat_government)
            }
            "03" -> {
                return getString(R.string.main_cat_food)
            }
            "04" -> {
                return getString(R.string.main_cat_store)
            }
            "05" -> {
                return getString(R.string.main_cat_personal)
            }
            "06" -> {
                return getString(R.string.main_cat_sport)
            }
            "07" -> {
                return getString(R.string.main_cat_education)
            }
            "08" -> {
                return getString(R.string.main_cat_health)
            }
            "09" -> {
                return getString(R.string.main_cat_room)
            }
            "10" -> {
                return getString(R.string.main_cat_auto)
            }
            "11" -> {
                return getString(R.string.main_cat_bussiness)
            }
            "12" -> {
                return getString(R.string.main_cat_services)
            }
            "13" -> {
                return getString(R.string.main_cat_finance)
            }
            "14" -> {
                return getString(R.string.main_cat_religion)
            }
            "15" -> {
                return getString(R.string.main_cat_transport)
            }
            "16" -> {
                return getString(R.string.main_cat_travel)
            }
            else -> {
                return getString(R.string.main_cat_travel)
            }
        }
    }


}
