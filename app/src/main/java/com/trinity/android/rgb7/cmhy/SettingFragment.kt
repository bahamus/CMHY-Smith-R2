package com.trinity.android.rgb7.cmhy

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase


class SettingFragment : Fragment() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.setting_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**  BEGIN Firebase Analytic */
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            param(FirebaseAnalytics.Param.SCREEN_NAME,"Setting")
        }
        /**  END Firebase Analytic */


        //List View Item Create
        view.findViewById<ListView>(R.id.list_view).apply {
            //setBackgroundColor(redColor)
            adapter = MyCustomAdapter(context) //This Nee to by Mycustom Adapter
            //onItemClickListener

        }
    }

    class MyCustomAdapter(context: Context) : BaseAdapter() {

        private val mContext: Context = context
        private val names = arrayListOf<String>( "ช่วยเราปรับปรุงข้อมูล","นโยบายการใช้งาน", "เกี่ยวกับเรา")

        //Response how many row in my list
        override fun getCount(): Int {
            return names.size
        }

        //You can also ignore this
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        //ignore for now
        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }


        //Response Rending out each rom
        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {

            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.row_setting, viewGroup, false)

            //Check Click Position
            rowMain.setOnClickListener {

                when (position) {
                    0 -> { //Goto Report Fragment
                        val action = SettingFragmentDirections.actionSettingFragmentToReportFragment()
                        rowMain.findNavController().navigate(action)
                        Log.d("Response", "Click Position = $position")
                    }
                    1 -> { //Goto Rule Fragment
                        val action = SettingFragmentDirections.actionSettingFragmentToRuleFragment()
                        rowMain.findNavController().navigate(action)
                        Log.d("Response", "Click Position = $position")
                    }
                    2 -> { //Goto About Fragment
                        val action = SettingFragmentDirections.actionSettingFragmentToAboutFragment()
                        rowMain.findNavController().navigate(action)
                        Log.d("Response", "Click Position = $position")
                    }

                    else -> {
                        Log.d("Response", "Null Data")
                    }
                }
            }


//            rowMain.findViewById<TextView>(R.id.position_textView).apply{
//                text = "Row Number: $position"
//            }

            rowMain.findViewById<TextView>(R.id.name_textView).apply {
                text = names[position]
            }
            return rowMain
        }

    }


    companion object {

    }
}