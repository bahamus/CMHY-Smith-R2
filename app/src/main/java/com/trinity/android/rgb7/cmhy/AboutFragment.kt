package com.trinity.android.rgb7.cmhy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase


class AboutFragment : Fragment() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.about_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**  BEGIN Firebase Analytic */
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            param(FirebaseAnalytics.Param.SCREEN_NAME,"About")
        }
        /**  END Firebase Analytic */

        /** BEGIN HTML Description Display */

        //Text Staff
        view.findViewById<TextView>(R.id.about_staff).apply {
            text = HtmlCompat.fromHtml(getString(R.string.about_staff), HtmlCompat.FROM_HTML_MODE_LEGACY)

        }

        //Text Report HTML
        view.findViewById<TextView>(R.id.about_text).apply {
            text = HtmlCompat.fromHtml(getString(R.string.about_document), HtmlCompat.FROM_HTML_MODE_LEGACY)

        }

        /** END HTML Description Display */


        /** BEGIN Button Jump Version History URL Display */
        view.findViewById<Button>(R.id.button_version).setOnClickListener(){
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cmhy.city/about-app"))
            startActivity(browserIntent)
        }
        /** END Button Jump Version History URL Display */
    }
}


