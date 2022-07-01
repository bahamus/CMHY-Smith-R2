package com.trinity.android.rgb7.cmhy

import android.app.Activity
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.trinity.android.rgb7.cmhy.main.ItemsRVAdapter
import com.trinity.android.rgb7.cmhy.repository.Repository


class PlaceListFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private val myAdapter by lazy { ItemsRVAdapter() }
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    val safeArgs: PlaceListFragmentArgs by navArgs()





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.place_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.text_response_error).isVisible = false


        //**Receive Argument from Category Fragment**
        val safeArgs: PlaceListFragmentArgs by navArgs()
        val catIdArgs = safeArgs.placeIdArgs

        /**  BEGIN Firebase Analytic */
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            param(FirebaseAnalytics.Param.SCREEN_NAME,"Place_List")
        }
        /**  END Firebase Analytic */


        /**  BEGIN Interstitial ADS  */



        /**  END Admob */



        /**  BEGIN Action Bar Tile Text */
        //====Action Bar Title======
        val actionBar = (activity as MainActivity).supportActionBar
        actionBar!!.setDisplayShowTitleEnabled(true)
        //Make Name for Call String value
        val catIdPlusName = "cat_$catIdArgs"
        actionBar.title = getString(resources.getIdentifier(catIdPlusName, "string",context?.packageName))

        /**  END  Action Bar Tile Text */

        /**  BEGIN Firebase Analytic */
        // Category ID
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT){
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "CatIDList")
            param(FirebaseAnalytics.Param.ITEM_ID,catIdPlusName)
        }
        /**  END Firebase Analytic */



        /**  BEGIN Admob */
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(requireContext())

        view.findViewById<AdView>(R.id.adViewBanner).apply {

            val adRequest = AdRequest.Builder().build()
            loadAd(adRequest)

        }

        /**  END Admob */


        /**  BEGIN Recycler Place List Json  */

        val placeListId = catIdArgs.toInt()  // Conver PlaceListId to Int
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)


        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getCustomPosts(placeListId, 0, "id", "desc")
        viewModel.myCustomPosts.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                view.findViewById<TextView>(R.id.text_response_error).isVisible = false
                response.body()?.let {
                    myAdapter.setData(it)
                }
            } else {
                view.findViewById<TextView>(R.id.text_response_error).isVisible
            }

        })

        view.findViewById<RecyclerView>(R.id.recyclerView)?.run {
            setHasFixedSize(true)
            //  ===  LIST VIEW Adapter ========
            adapter = myAdapter
            layoutManager = LinearLayoutManager(context)
        }
        /**  END Recycler Place List Json  */

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list_data, menu)
    }

    //Todo Option Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val catIdArgsToMap = safeArgs.placeIdArgs
        val id = item.itemId
        if (id == R.id.searching) {
            val action = PlaceListFragmentDirections.actionFlowPlaceListDestToFlowPlaceSearchDest()
            findNavController().navigate(action)
        }else if (id == R.id.map_list) {
            val action = PlaceListFragmentDirections.actionFlowPlaceListDestToPlaceListMapList(catIdArgsToMap)
            findNavController().navigate(action)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


}
