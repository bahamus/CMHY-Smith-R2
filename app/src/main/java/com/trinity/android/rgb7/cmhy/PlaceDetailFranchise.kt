package com.trinity.android.rgb7.cmhy

import android.app.Activity
import android.content.ContentValues
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
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
import com.trinity.android.rgb7.cmhy.main.ItemFCAdapter
import com.trinity.android.rgb7.cmhy.repository.Repository


class PlaceDetailFranchise : Fragment() {

    private lateinit var viewModel: MainViewModel
    private val myAdapter by lazy { ItemFCAdapter() }
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    //**Receive Argument from Main Fragment**
    val safeArgs: PlaceDetailFranchiseArgs by navArgs()
    private var mInterstitialAd: InterstitialAd? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.place_detail_franchise_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       // val placeIdCat = safeArgs.placeIdArgs
        val franchise = safeArgs.franchiseArgs

        //==  Text Title Action Bar ====
        val actionBar = (activity as MainActivity).supportActionBar
        actionBar!!.setDisplayShowTitleEnabled(true)
        actionBar.title = franchise


        /**  BEGIN Firebase Analytic */
        firebaseAnalytics = Firebase.analytics

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            param(FirebaseAnalytics.Param.SCREEN_NAME,"Franchise")
        }
        //Franchise ID
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT){
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "FranchiseID")
            param(FirebaseAnalytics.Param.ITEM_ID,franchise)
        }
        /**  END Firebase Analytic */
        /**  BEGIN Interstitial ADS  */
        // ca-app-pub-4619737788076129/2759313796  REAL CODE
        // ca-app-pub-3940256099942544/1033173712 CODE TEST
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(context,"ca-app-pub-4619737788076129/7276287070", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(ContentValues.TAG, adError.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(ContentValues.TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })

        //LOG
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(ContentValues.TAG, "Ad was dismissed.")
            }
            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(ContentValues.TAG, "Ad failed to show.")
            }
            override fun onAdShowedFullScreenContent() {
                Log.d(ContentValues.TAG, "Ad showed fullscreen content.")
                mInterstitialAd = null;
            }
        }

        //Show ADS
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(context as Activity)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
        /**  END Interstitial ADS  */

        /**  BEGIN Admob */
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(context)

        view.findViewById<AdView>(R.id.adViewBanner).apply {

            val location = Location("AdMobProvider")
            location.latitude = 18.790218528898254
            location.latitude = 98.98770239881131
            val adBuilder = AdRequest.Builder()
            adBuilder.setLocation(location)
            adBuilder.addKeyword("เชียงใหม่");

            val adRequest = AdRequest.Builder().build()
            loadAd(adRequest)


        }

        /**  END Admob */

        /**  BEGIN Recycler Place List Json  */

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)


        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        val textSeach = "franchise-$franchise"
        viewModel.getCustomPostsSearch(textSeach, "KFC", "id", "desc")

        viewModel.myCustomPostsSearch.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                response.body()?.let { it ->
                    //ItemsRVAdapter.setData(it)
                    myAdapter.setData(it)
                    Log.d("Response","Success")
                    response.body()!!.forEach {
                        Log.d("Response",it.ID.toString())
                    }
                }
            } else {
                Log.d("Response","UnSucces!!")
               // Log.d("Response","Place id = $placeListId")
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

        inflater.inflate(R.menu.menu_search, menu)
    }

    //Todo Option Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val franchiseSearch = safeArgs.franchiseArgs
        item.isEnabled = false

        if (id == R.id.map_list) {
            val action = PlaceDetailFranchiseDirections.actionPlaceDetailFranchiseToPlaceDetailFranchiseMapList(franchiseSearch)
            findNavController().navigate(action)
        }

        return super.onOptionsItemSelected(item)
    }
}