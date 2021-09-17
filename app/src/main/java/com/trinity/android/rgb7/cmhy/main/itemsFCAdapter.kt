package com.trinity.android.rgb7.cmhy.main

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.trinity.android.rgb7.cmhy.*

import com.trinity.android.rgb7.cmhy.model.PostPlaceList


class ItemFCAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var myList = emptyList<PostPlaceList>()

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return myList.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//     user Searching Row Layout
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_franchise, parent, false))
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder

        //ICON Category
        itemViewHolder.itemView.findViewById<ImageView>(R.id.icon_category).apply {
            //Image Icon Resources
            val catIdResImg = myList[position].catID
            setImageResource(
                    resources.getIdentifier(
                            "ic_group_$catIdResImg",
                            "drawable",
                            context.packageName
                    )
            )
            //Set background Image Color
            val bgColorPlus = "#" + myList[position].bgcolor
            setBackgroundColor(Color.parseColor(bgColorPlus))
        }

//        itemViewHolder.itemView.findViewById<TextView>(R.id.id_txt).apply {
//            text = myList[position].ID.toString()
//        }

        itemViewHolder.itemView.findViewById<TextView>(R.id.title_txt).apply {
            val statusCheck = myList[position].status
            text = if (statusCheck == "Deactive") {
                "    "+myList[position].name.take(50)+"..."
            } else {
                myList[position].name.take(50)+"..."
            }

        }



        //Icon Status
        itemViewHolder.itemView.findViewById<ImageView>(R.id.icon_status_close).apply {
            val statusCheck = myList[position].status
            isVisible = if (statusCheck == "Deactive") {
                setImageResource(R.drawable.ic_status_icon_warning)
                true
            } else {
                false
            }
        }



        itemViewHolder.itemView.setOnClickListener{
            //var CLickID = String
            val cLickID = myList[position].ID.toString()
            Log.d("CheckClick","Click ID = $cLickID")

            //Jump to Place Detail

            val action = PlaceDetailFranchiseDirections.actionPlaceDetailFranchiseToFlowPlaceDetailDest(cLickID)
            itemViewHolder.itemView.findNavController().navigate(action)
        }


    }




    fun setData(newList: List<PostPlaceList>){
        myList = newList
        notifyDataSetChanged()
    }

}