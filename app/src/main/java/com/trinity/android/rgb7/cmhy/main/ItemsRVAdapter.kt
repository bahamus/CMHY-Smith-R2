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
import com.trinity.android.rgb7.cmhy.PlaceListFragmentDirections
import com.trinity.android.rgb7.cmhy.R
import com.trinity.android.rgb7.cmhy.model.PostPlaceList


class ItemsRVAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var myList = emptyList<PostPlaceList>()

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return myList.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ItemViewHolder(binding)
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))
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


        itemViewHolder.itemView.findViewById<TextView>(R.id.title_txt).apply {
            val statusCheck = myList[position].status
            text = if (statusCheck == "Deactive") {
                "    "+myList[position].name
            } else {
                myList[position].name
            }

        }

        itemViewHolder.itemView.findViewById<TextView>(R.id.tags_txt).apply {
            val tagPlusString = myList[position].tags.take(50)
            text = "$tagPlusString..."
        }


        //Icon Status
        itemViewHolder.itemView.findViewById<ImageView>(R.id.icon_status_close).apply {
            val statusCheck = myList[position].status
            Log.d("Response", "Status ID = $statusCheck")
            isVisible = if (statusCheck == "Deactive") {
                setImageResource(R.drawable.ic_status_icon_warning)
                true
            } else {
                false
            }
        }


        //On ClickListerner
        itemViewHolder.itemView.setOnClickListener {
            //var CLickID = String
            var CLickID = myList[position].ID.toString()
            Log.d("CheckClick", "Click ID = $CLickID")


            val action = PlaceListFragmentDirections.actionPlaceListFragmentToPlaceDetailFragment(CLickID)

            //Navigation.createNavigateOnClickListener(action)
            itemViewHolder.itemView.findNavController().navigate(action)
        }


    }


    fun setData(newList: List<PostPlaceList>) {
        myList = newList
        notifyDataSetChanged()
    }

}