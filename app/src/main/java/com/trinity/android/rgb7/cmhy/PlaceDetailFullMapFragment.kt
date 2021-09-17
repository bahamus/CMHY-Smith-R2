package com.trinity.android.rgb7.cmhy

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase


class PlaceDetailFullMapFragment : Fragment() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    //Innitail Variable Declaration GPS
    var googleLat: Double = 0.0
    var googleLng: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.place_detail_full_map_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**  BEGIN Firebase Analytic */
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            param(FirebaseAnalytics.Param.SCREEN_NAME,"Full_Map")
        }
        /**  END Firebase Analytic */

         val safeArgs: PlaceDetailFullMapFragmentArgs by navArgs()
         val googleLatM = safeArgs.LatArgs
         val googleLngM = safeArgs.LngArgs
         val placeNameArgsValue = safeArgs.placeNameArgs

        /**  BEGIN Action Bar Tile Text */
        //====Action Bar Title======
        val actionBar = (activity as MainActivity).supportActionBar
        actionBar!!.setDisplayShowTitleEnabled(true)
        actionBar.title = placeNameArgsValue

        /**  BEGIN Recycler Place List Json  */

         googleLat = googleLatM.toDouble()
         googleLng =  googleLngM.toDouble()


        /**------BEGIN Google Map API ------------------ */
        //Call Google map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        /**------END Google Map API ------------------ */
        Log.d("Response","placeNameArgsValue $placeNameArgsValue")
       Log.d("Response","Lat = $googleLatM Lng = $googleLngM")


    }

    private val callback = OnMapReadyCallback { googleMap ->
        //For Test Three King
        // val latitude = 18.790249
        //val longitude =  98.987461

        /** BEGIN Set Google Map  */


        val newLatLag = LatLng(googleLat, googleLng)
        val zoomLevel = 15f

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLag, zoomLevel))
        googleMap.addMarker(MarkerOptions()
                .position(newLatLag)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
        )

        /** END Set Google Map  */


        /** BEGIN UI Setting  */
        with(googleMap.uiSettings) {

            isZoomControlsEnabled = true
            isCompassEnabled = true
            isScrollGesturesEnabled = true
            isMapToolbarEnabled = true
            isTiltGesturesEnabled = true
            isZoomGesturesEnabled = true
            isMyLocationButtonEnabled = true
            isMapToolbarEnabled = true

        }
        /** END UI Setting  */

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_full_map, menu)
    }

    //Todo Option Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.google_navigator) {
            val jumpWorldLat = googleLat.toString()
            val jumpWorldLng = googleLng.toString()


            //Jump To Google Navigator
//            val jumpWorldLat = googleLat.toString()
//            val jumpWorldLng = googleLng.toString()
//            val gmmIntentUri: Uri = Uri.parse("google.navigation:q=$jumpWorldLat,$jumpWorldLng") //GPS
//            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//            mapIntent.setPackage("com.google.android.apps.maps")
//            startActivity(mapIntent)
            try { //IF Google Navigator
                val gmmIntentUri: Uri = Uri.parse("google.navigation:q=$jumpWorldLat,$jumpWorldLng") //GPS
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            } catch (e: ActivityNotFoundException) { //If Nokia Mobile
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/@?api=1&map_action=map@$jumpWorldLat,$jumpWorldLng"))
                startActivity(browserIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {


    }
}