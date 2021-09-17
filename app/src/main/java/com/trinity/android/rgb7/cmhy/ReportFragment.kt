package com.trinity.android.rgb7.cmhy

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html.fromHtml
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.google.firebase.analytics.FirebaseAnalytics


class ReportFragment : Fragment() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.report_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** BEGIN HTML Description Display */
        //Text Report HTML
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
            view.findViewById<TextView>(R.id.report_text).apply {
                text = HtmlCompat.fromHtml(getString(R.string.report_document), HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
        } else {
            view.findViewById<TextView>(R.id.report_text).apply {
                text = fromHtml(getString(R.string.report_document))
            }
        }
        /** END HTML Description Display */


        //Button Jump URL
        view.findViewById<Button>(R.id.url_contact_button).setOnClickListener() {
            val urlContact = "https://www.cmhy.city/contact/"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlContact)))
        }


    }

    companion object {

    }
}