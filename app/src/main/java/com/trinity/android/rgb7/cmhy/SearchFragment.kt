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


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.trinity.android.rgb7.cmhy.main.itemSCAdapter
import com.trinity.android.rgb7.cmhy.repository.Repository


class SearchFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private val myAdapter by lazy { itemSCAdapter() }
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    var textSearch: String = "null"


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment

       return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**  BEGIN Firebase Analytic */
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            param(FirebaseAnalytics.Param.SCREEN_NAME,"Search")
        }
        /**  END Firebase Analytic */


        //====Action Bar Title======
        /**  BEGIN Action Bar Tile Text */

        val actionBar = (activity as MainActivity).supportActionBar
        actionBar!!.setDisplayShowTitleEnabled(true)
        //actionBar.setIcon(null)

        /**  END  Action Bar Tile Text */




        /** BEGIN SEARCHView  **/
        view.findViewById<SearchView>(R.id.search_view).apply {
            //  val query: CharSequence = search_view.getQuery()
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(p0: String?): Boolean {
                    clearFocus()
                    if (p0 != null) {
                        // Search Search Key Word
                        textSearch = p0
                        viewModel.getCustomPostsSearch(textSearch, "The Truth Is Coming", "id", "desc")
//                        Log.d("Response", "On View Model textSearch = $textSearch")
                        /**  BEGIN Firebase Analytic */
                        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH){
                            param(FirebaseAnalytics.Param.SEARCH_TERM,textSearch)
                        }
                        /**  END Firebase Analytic */
                    } else {
                        Log.d("Response", "Not Searching or Waiting Search text Searcgh= $textSearch")
                    }

                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    // myAdapter?.filter?.filter(p0)
                    return false
                }
            })
        }

        /** END SEARCHView  **/


        /**  BEGIN Recycler Searching Json  */

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.myCustomPostsSearch.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                response.body()?.let { it ->
                    //ItemsRVAdapter.setData(it)
                    myAdapter.setData(it)
                    Log.d("Response", "Success")
                    response.body()!!.forEach {
                        Log.d("Response", it.ID.toString())
                    }
                }
                view.findViewById<RecyclerView>(R.id.recyclerView)?.run {
                    setHasFixedSize(true)
                    //  ===  LIST VIEW Adapter ========
                    adapter = myAdapter
                    layoutManager = LinearLayoutManager(context)
                }
                /**  END Recycler Searching Json  */
            } else {
                view.findViewById<RecyclerView>(R.id.recyclerView).isEnabled = false
                Log.d("Response", "UnSucces!!")
            }

        })




    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //Disable

            //menu.findItem(R.id.map_list).isEnabled = false


        inflater.inflate(R.menu.menu_search, menu)
    }

    //Todo Option Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        item.isEnabled = false

        if (id == R.id.map_list) {
            val action = SearchFragmentDirections.actionFlowPlaceSearchDestToSearchMapList(textSearch)
            findNavController().navigate(action)
        }

        return super.onOptionsItemSelected(item)
    }


}