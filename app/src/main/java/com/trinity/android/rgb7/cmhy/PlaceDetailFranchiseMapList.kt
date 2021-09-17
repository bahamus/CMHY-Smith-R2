package com.trinity.android.rgb7.cmhy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.trinity.android.rgb7.cmhy.model.PostPlaceList
import com.trinity.android.rgb7.cmhy.repository.Repository


class PlaceDetailFranchiseMapList : Fragment() {

    //Innitail Variable Declaration GPS
    var googleLat: Double = 0.0
    var googleLng: Double = 0.0

    private lateinit var viewModel: MainViewModel
    private val safeArgs: PlaceDetailFranchiseMapListArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.place_detail_franchise_map_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //**Receive Argument from Place List**
        val SearchID = safeArgs.placeIdArgs


        //====Action Bar Title======
        val actionBar = (activity as MainActivity).supportActionBar
        actionBar!!.setDisplayShowTitleEnabled(true)
        //Search Text
        actionBar.title = SearchID


        //Update Value Lat , Lng
        googleLat = 18.790249
        googleLng = 98.987461
        /** --------Google Map  ---------------- */
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(

                OnMapReadyCallback { googleMap ->
                    //Initial Map GPS to Three King
                    val threeKingLatLng = LatLng(googleLat, googleLng)
                    val zoomLevel = 13f
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(threeKingLatLng, zoomLevel))

                    //Custom Window Info Hidden ID
                    googleMap.setInfoWindowAdapter(CustomInfoWindowAdapter())

                    //TODO BEGIN Json Phase
                    val repository = Repository()
                    val viewModelFactory = MainViewModelFactory(repository)
                    val textSeach = "franchise-$SearchID"
                    viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
                    viewModel.getCustomPostsSearchMapList(textSeach, "The Truth Is Coming", "id", "desc")
                    viewModel.myCustomPostsSearch.observe(viewLifecycleOwner, Observer { response ->

                        val items = response.body()
                        if (response.isSuccessful) {
                            var itemJson: PostPlaceList?
                            val itemCount = items?.count()


                            /** BEGIN Loop  Load Data CMHY On Server in Apps */
                            for (i in 0 until itemCount!!) {
                                itemJson = items[i]
                                val catIdResImg = itemJson.catID
                                Log.d("Response", "item list data = $items")


                                /** BEGIN Pin Marker  */
                                if (itemJson.status == "Active") {
                                    googleMap.addMarker(
                                            MarkerOptions()
                                                    .position(LatLng(itemJson.latitude.toDouble(), itemJson.longitude.toDouble()))
                                                    .title(itemJson.name)
                                                    .snippet("${itemJson.ID}")
                                                    .alpha(1.0F)
                                                    .anchor(0F,1F)
                                                    .icon(BitmapDescriptorFactory.fromResource(resources.getIdentifier(
                                                            "ic_map_$catIdResImg",
                                                            "drawable",
                                                            context?.packageName
                                                    ))
                                                    ))
                                }
                                else{ // Alpha Marker Of Status Close Service
                                    googleMap.addMarker(
                                            MarkerOptions()
                                                    .position(LatLng(itemJson.latitude.toDouble(), itemJson.longitude.toDouble()))
                                                    .title("*"+itemJson.name)
                                                    .snippet("${itemJson.ID}")
                                                    .alpha(1.0F)
                                                    .anchor(0F,1F)
                                                    .icon(BitmapDescriptorFactory.fromResource(resources.getIdentifier(
                                                            "ic_map_close_$catIdResImg",
                                                            "drawable",
                                                            context?.packageName
                                                    ))
                                                    ))
                                }
                                /** END Pin Marker  */
                            }

                            /** END Loop  Load Data CMHY On Server in Apps */
                        } else {
                            Log.d("Response", "Error Response data")
                        }
                    })

                    //--------BEGIN  Marker 01----------------
                    googleMap.setOnInfoWindowClickListener { marker01 ->
                        onMarkerClick(marker01)
                    }


                    /** BEGIN UI Setting  */


                    with(googleMap.uiSettings) {

                        isZoomControlsEnabled = true
                        isCompassEnabled = true
                        isScrollGesturesEnabled = true
                        isMapToolbarEnabled = true
                        isTiltGesturesEnabled = true
                        isZoomGesturesEnabled = true
                    }
                    /** END UI Setting  */
                }
        )
        /** --------ENd Google Map  ---------------- */
    }

    private fun onMarkerClick(marker: Marker) {

        val placeId = marker.snippet.toString()
        val action = PlaceDetailFranchiseMapListDirections.actionPlaceDetailFranchiseMapListToFlowPlaceDetailDest(placeId)
        findNavController().navigate(action)
    }

    /** Demonstrates customizing the info window and/or its contents.  */
    internal inner class CustomInfoWindowAdapter : GoogleMap.InfoWindowAdapter {

        private val contents: View = layoutInflater.inflate(R.layout.custom_info_content, null)

        override fun getInfoWindow(marker: Marker): View? {
            return null
        }

        override fun getInfoContents(marker: Marker): View? {
            //Crete Window Info and Content on new window infocontent
            render(marker, contents)
            return contents
        }

        private fun render(marker: Marker, view: View) {
            //Prepare Write Title Text and disable snipet text on layout
            //Rendering Text Title on Window Info
            val title: String? = marker.title
            val textStatusCheck : String
            view.findViewById<TextView>(R.id.title)?.apply {
                text = title
            }

            //Check Status and Display Icon
            if (title != null) {
                textStatusCheck = title.substring(0,1)
                if(textStatusCheck=="*"){ //Status Close
                    view.findViewById<ImageView>(R.id.icon_status_close).isVisible = true
                        Log.d("Response", "Close text = $textStatusCheck ")
                }
                else{ //Status Open
                    Log.d("Response", "Open  text = $textStatusCheck ")
                    view.findViewById<ImageView>(R.id.icon_status_close).isVisible = false
                }
            }
        }
    }


}