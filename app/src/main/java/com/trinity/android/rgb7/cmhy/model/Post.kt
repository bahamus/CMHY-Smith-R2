package com.trinity.android.rgb7.cmhy.model

//Todo Json Data Place Detail
data class Post(

      val ID:Int,
      val bgcolor: String,
      val catID: String,
      val name:String,
      val address:String,
      val open_time:String,
      val tel_no:String,
      val link:String,
      val latitude:String,
      val longitude:String,
      val tags:String,
      val description:String,
      val photo1:String,
      val photo2:String,
      val photo3:String,
      val franchise:String,
      val status:String

)

//Todo Json Data Place List & Searching
data class PostPlaceList(

        val ID: Int,
        val name: String,
        val catID: String,
        val bgcolor: String,
        val franchise: String,
        val latitude: String,
        val longitude: String,
        val tags: String,
        val status:String

)