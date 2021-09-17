package com.trinity.android.rgb7.cmhy

import android.app.Activity
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase


class PlaceDetailDescription : Fragment() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.place_detail_description_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**  BEGIN Firebase Analytic */
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            param(FirebaseAnalytics.Param.SCREEN_NAME, "Place_Detail_Description")
        }
        /**  END Firebase Analytic */

        /**  BEGIN Admob Samart Banner*/
        // Initialize Smart Banner at top Image
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
        /**  END Admob Samart Banner */




        //Receive Argument
        val safeArgs : PlaceDetailDescriptionArgs by navArgs()
        val placeId = safeArgs.PlaceIdArgs


        /** BEGIN WEB VIEW */
        val  mWebView = view.findViewById(R.id.myURL) as WebView


        mWebView.loadUrl("https://cmhy.city/app/place_description/$placeId")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

//            webSettings.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
//            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
        }

        /** END WEB VIEW */

        Log.d("Response", "Place ID = $placeId")

    }

    companion object {

    }
}