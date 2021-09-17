package com.trinity.android.rgb7.cmhy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase


class RuleFragment : Fragment() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.rule_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**  BEGIN Firebase Analytic */
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            param(FirebaseAnalytics.Param.SCREEN_NAME,"Rule")
        }
        /**  END Firebase Analytic */

        /** BEGIN HTML Description Display */

        //Text Report HTML
        view.findViewById<TextView>(R.id.rule_text).apply {
            text = HtmlCompat.fromHtml(getString(R.string.rule_document), HtmlCompat.FROM_HTML_MODE_LEGACY)

        }

        /** END HTML Description Display */
    }



    companion object {

    }
}