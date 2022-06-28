package com.trinity.android.rgb7.cmhy

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.trinity.android.rgb7.cmhy.repository.Repository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class PlaceDetailFragment : Fragment() {


    private lateinit var viewModel: MainViewModel
    private val safeArgs: PlaceDetailFragmentArgs by navArgs()
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    //Innitail Variable Declaration GPS
    var googleLat: Double = 0.0
    var googleLng: Double = 0.0
    lateinit var placeNameVal: String




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.place_detail_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //**Receive Argument from Main Fragment**
        val placeID = safeArgs.placeIdArgs

        /**  BEGIN Firebase Analytic */
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            param(FirebaseAnalytics.Param.SCREEN_NAME,"Place_Detail")
        }


        /**  END Firebase Analytic */



        /**  BEGIN Admob Samart Banner*/
        // Initialize Smart Banner at top Image
        MobileAds.initialize(context)
        view.findViewById<AdView>(R.id.adViewBanner).apply {


            val adRequest = AdRequest.Builder().build()
            loadAd(adRequest)

        }
        /**  END Admob Samart Banner */



        //TODO BEGIN Json Phase
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost2(Integer.parseInt(placeID))
        viewModel.myResponse2.observe(viewLifecycleOwner, Observer { response ->
            /**------BEGIN Response Success------------------ */
            if (response.isSuccessful) {

                placeNameVal = response.body()?.name.toString()

                /**------BEGIN Load Data CMHY On Server in Apps------------------ */


                //Header BgColor
                view.findViewById<ConstraintLayout>(R.id.headerBackground).apply {
                    val bgColorPlus = "#" + response.body()?.bgcolor
                    setBackgroundColor(Color.parseColor(bgColorPlus))
                }

                //ICON Categoryicon_category
                view.findViewById<ImageView>(R.id.button_group_header).apply {
                    //Image Icon Resources
                    val catIdResImg = response.body()?.catID
                    setImageResource(
                            resources.getIdentifier(
                                    "ic_group_$catIdResImg",
                                    "drawable",
                                    context.packageName
                            )
                    )
                }

                //Check Status Close?
                if (response.body()?.status == "Active") {
                    view.findViewById<ConstraintLayout>(R.id.layout_place_close).apply {
                        isVisible = false
                    }
                    view.findViewById<ImageView>(R.id.icon_status_close).apply{
                        isVisible = false
                    }

                } else {
                    view.findViewById<ConstraintLayout>(R.id.layout_place_close).apply {
                        isVisible = true
                        setBackgroundColor(Color.parseColor("#000000"))
                    }
                    view.findViewById<ImageView>(R.id.icon_status_close).apply{
                        isVisible = true
                    }
                }


                //NAME PLACE
                view.findViewById<TextView>(R.id.name_place_text).apply {
                    text = response.body()?.name

                    /**  BEGIN Firebase Analytic */
                    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT){
                        param(FirebaseAnalytics.Param.CONTENT_TYPE, "Place Name")
                        param(FirebaseAnalytics.Param.ITEM_ID,response.body()?.name.toString())
                    }
                    /**  END Firebase Analytic */

                }
                //ADDRESS
                view.findViewById<TextView>(R.id.address_text).apply {
                    text = response.body()?.address
                }
                //OPEN TIME
                view.findViewById<TextView>(R.id.open_time_text).apply {
                    text = response.body()?.open_time
                }
                //TEL NUMBER

                /**------BEGIN CHECK Status Close TEL.Number------------------ */

                if (response.body()?.status == "Active") {
                    val temp1:String = response.body()?.tel_no.toString()
                    if(!temp1.equals("")) {
                        var telNo:String
                        val temp2: List<String> = temp1.split(',')
                        val size:Int = temp2.size
                        telNo = temp2[0].toString()
                        view.findViewById<TextView>(R.id.tel_01_text).apply {
                            text = telNo
                        }
                        //**Tel No.3 Hidden space = 0**
                        if(size==1) {
                            view.findViewById<TextView>(R.id.tel_03_text).isInvisible
                            view.findViewById<TextView>(R.id.tel_03_text).apply {
                                height = 0
                            }
                        }

                        if(size>1) {
                            telNo = temp2[1].toString()
                            view.findViewById<TextView>(R.id.tel_02_text).apply {
                                text = telNo
                            }
                            //**Tel No.3 Hidden space = 0**
                            if(size==2) {
                                view.findViewById<TextView>(R.id.tel_03_text).isInvisible
                                view.findViewById<TextView>(R.id.tel_03_text).apply {
                                    height = 0
                                }
                            }

                        }
                        if(size>2) {
                            telNo = temp2[2].toString()
                            view.findViewById<TextView>(R.id.tel_03_text).apply {
                                text = telNo
                            }
                            //Tel No.3 Hidden
                            view.findViewById<TextView>(R.id.tel_03_text).visibility

                        }
                    }
                } else { // Deactive
                    view.findViewById<TextView>(R.id.tel_01_text).apply {
                        text = null
                    }
                }


                /**------END CHECK Status Close TEL.Number------------------ */
                // LINK
                view.findViewById<TextView>(R.id.link_text).apply {
                    text = response.body()?.link
                }

                view.findViewById<TextView>(R.id.lat_text).apply {
                    text = response.body()?.latitude
                }
                view.findViewById<TextView>(R.id.lng_text).apply {
                    text = response.body()?.longitude
                }
                view.findViewById<TextView>(R.id.tag_text).apply {
                    text = response.body()?.tags
                }

                /**------END Load Data CMHY On Server in Apps------------------ */

                /**------BEGIN Check& Display Photo------------------ */
                response.body()?.let { it1 -> Log.d("Response", it1.name) } //Print Name Check

                //Loading Picture JSon CMHYServ
                if (response.body()?.photo1?.length != 0) {  //Check Data Photo null?

                    when {
                        response.body()?.photo2?.length == 0 -> { //Check Photo2 null ?
                            //Display Photo 1
                            view.findViewById<ImageView>(R.id.imageView01).apply {
                                Picasso.get()
                                        .load(response.body()?.photo1)
                                        .placeholder(R.drawable.nophoicon)
                                        .error(R.drawable.nophoicon_error)
                                        .into(view.findViewById<ImageView>(R.id.imageView01))
                                Log.d("Response", "Photo1 Only ")
                            }
                            view.findViewById<ImageView>(R.id.imageView02).isVisible = false
                            view.findViewById<ImageView>(R.id.imageView03).isVisible = false
                        }
                        response.body()?.photo3?.length == 0 -> { //Check Photo3 null ?
                            //Display Photo 1
                            view.findViewById<ImageView>(R.id.imageView01).apply {
                                Picasso.get()
                                        .load(response.body()?.photo1)
                                        .placeholder(R.drawable.nophoicon)
                                        .error(R.drawable.nophoicon_error)
                                        .into(view.findViewById<ImageView>(R.id.imageView01))
                                Log.d("Response", "Photo1  ")
                            }
                            view.findViewById<ImageView>(R.id.imageView03).isVisible = false

                            //Display Photo 2
                            view.findViewById<ImageView>(R.id.imageView02).apply {
                                Picasso.get()
                                        .load(response.body()?.photo2)
                                        .placeholder(R.drawable.nophoicon)
                                        .error(R.drawable.nophoicon_error)
                                        .into(view.findViewById<ImageView>(R.id.imageView02))
                                Log.d("Response", "+Photo2 ")
                            }


                        }
                        else -> { // Display All Photo 1,2,3
                            //Display Photo 1
                            view.findViewById<ImageView>(R.id.imageView01).apply {
                                Picasso.get()
                                        .load(response.body()?.photo1)
                                        .placeholder(R.drawable.nophoicon)
                                        .error(R.drawable.nophoicon_error)
                                        .into(view.findViewById<ImageView>(R.id.imageView01))
                                Log.d("Response", "Photo1")
                            }

                            //Display Photo 2
                            view.findViewById<ImageView>(R.id.imageView02).apply {
                                Picasso.get()
                                        .load(response.body()?.photo2)
                                        .placeholder(R.drawable.nophoicon)
                                        .error(R.drawable.nophoicon_error)
                                        .into(view.findViewById<ImageView>(R.id.imageView02))
                                Log.d("Response", "+Photo2 ")
                            }
                            //Display Photo 3
                            view.findViewById<ImageView>(R.id.imageView03).apply {
                                Picasso.get()
                                        .load(response.body()?.photo3)
                                        .placeholder(R.drawable.nophoicon)
                                        .error(R.drawable.nophoicon_error)
                                        .into(view.findViewById<ImageView>(R.id.imageView03))
                                Log.d("Response", "+Photo3 ")
                            }

                        }
                    }

                } else { //No Picture
                    Log.d("Response", "Photo Data is null!!!")
                    view.findViewById<ImageView>(R.id.imageView02).isVisible = false
                    view.findViewById<ImageView>(R.id.imageView03).isVisible = false
                }

                /**------END Check& Display Photo------------------ */


                //Check FlowStepNumber
                Log.d("Response", "ID = $placeID")

                /**------BEGIN Google Map API ------------------ */
                googleLat = response.body()?.latitude?.toDouble()!!
                googleLng = response.body()?.longitude?.toDouble()!!
                //Call Google map
                val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                mapFragment?.getMapAsync(callback)

                /**------END Google Map API ------------------ */

                /**  BEGIN Button Franchise  */


                if (response.body()?.franchise != "-")  //Check Franchise null?
                {
                    //Display Button Franchise
                    view.findViewById<Button>(R.id.franchise_button).isVisible = true
                    view.findViewById<Button>(R.id.franchise_button).text = getString(R.string.subMenuFranchise)

                    view.findViewById<Button>(R.id.franchise_button)?.setOnClickListener {
                        val franchiseId = response.body()?.franchise.toString()
                        val action = PlaceDetailFragmentDirections.actionFlowPlaceDetailDestToPlaceDetailFranchise(franchiseId)
                        findNavController().navigate(action)

                    }
                } else {
                    view.findViewById<Button>(R.id.franchise_button).isVisible = true
                    view.findViewById<Button>(R.id.franchise_button).apply {
                        isEnabled = false
                        setTextColor(Color.GRAY)
                    }
                }
                /**  END Button Franchise  */


                /**------BEGIN  JUMP Display Button  ------------------ */


                //Goto Full Map
                view.findViewById<Button>(R.id.fullmap_button)?.setOnClickListener {
                    //val placeNameVal = response.body()?.name.toString()
                    Log.d("Response", "Place Detail $placeNameVal")
                    val action = PlaceDetailFragmentDirections.actionFlowPlaceDetailDestToPlaceDetailFullMapFragment(googleLat.toString(), googleLng.toString(), placeNameVal)
                    findNavController().navigate(action)
                }

                //Goto Description

                if (response.body()?.description?.isNotEmpty() == true) { //Display Button Description
                    view.findViewById<Button>(R.id.description_button)?.setOnClickListener {
                        val action = PlaceDetailFragmentDirections.actionFlowPlaceDetailDestToPlaceDetailDescription(placeID)
                        findNavController().navigate(action)
                    }
                } else {
                    //view.findViewById<Button>(R.id.description_button).isEnabled = false
                    view.findViewById<Button>(R.id.description_button).isVisible = false

                }

                //Goto Navigator
                view.findViewById<Button>(R.id.navigator_button).setOnClickListener{
                    //Jump To Google Navigator
                    val jumpWorldLat = googleLat.toString()
                    val jumpWorldLng = googleLng.toString()
                    val gmmIntentUri: Uri = Uri.parse("google.navigation:q=$jumpWorldLat,$jumpWorldLng") //GPS
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
                }

                view.findViewById<Button>(R.id.report_button)?.setOnClickListener {
                    val action = PlaceDetailFragmentDirections.actionFlowPlaceDetailDestToReportFragment()
                    findNavController().navigate(action)
                }


                //Share Button
                view.findViewById<Button>(R.id.share_button).setOnClickListener{
                    val sendIntent: Intent = Intent().apply {
                        // (Optional) Here we're setting the title of the content
                        putExtra(Intent.EXTRA_TITLE, "$placeNameVal")
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "https://www.cmhy.city/place/$placeID")
                        type = "text/plain"

                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }




                /**------END JUMP Display Button  ------------------ */






            }
            /**------END Response Success------------------ */
            else {
                Log.d("Response", "Error Response data")
            }
        })

        //TODO END Json Phase


    }

    private val callback = OnMapReadyCallback { googleMap ->
        /** BEGIN Set Google Map  */

        val newLatLng = LatLng(googleLat, googleLng)
        val zoomLevel = 15f

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, zoomLevel))
        googleMap.addMarker(MarkerOptions()
                .position(newLatLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
        )

        /** END Set Google Map  */


        /** BEGIN UI Setting  */
        with(googleMap.uiSettings) {

            isZoomControlsEnabled = true
            isCompassEnabled = true
            isScrollGesturesEnabled = false
            isMapToolbarEnabled = true
            isTiltGesturesEnabled = true
            isZoomGesturesEnabled = true

        }
        /** END UI Setting  */


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_place_detail, menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return when (item.itemId) {
            R.id.searching -> {
                //Go to Searching
                val action = PlaceDetailFragmentDirections.actionFlowPlaceDetailDestToFlowPlaceSearchDest()
                findNavController().navigate(action)
                return true
            }
            R.id.fullmap_dest -> {
                //View Full Map
                val action = PlaceDetailFragmentDirections.actionFlowPlaceDetailDestToPlaceDetailFullMapFragment(googleLat.toString(), googleLng.toString(), placeNameVal)
                findNavController().navigate(action)
                return true
            }
            R.id.googledrive_dest -> {
                //Jump To Google Navigator


                val jumpWorldLat = googleLat.toString()
                val jumpWorldLng = googleLng.toString()

                try { //IF Google Navigator
                    val gmmIntentUri: Uri = Uri.parse("google.navigation:q=$jumpWorldLat,$jumpWorldLng") //GPS
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
                } catch (e: ActivityNotFoundException) { //If Nokia Mobile
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/@?api=1&map_action=map@$jumpWorldLat,$jumpWorldLng"))
                    startActivity(browserIntent)
                }
                return true
            }

            // REM Next Vesion
//            R.id.report_dest -> {
//                //Goto Report Page
//                val action = PlaceDetailFragmentDirections.actionFlowPlaceDetailDestToReportFragment()
//                findNavController().navigate(action)
//                return true
//            }
            R.id.setting_dest -> {
                //Goto Rule Page
                val action = PlaceDetailFragmentDirections.actionFlowPlaceDetailDestToSetting()
                findNavController().navigate(action)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }


}