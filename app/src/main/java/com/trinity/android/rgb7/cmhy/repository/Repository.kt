package com.trinity.android.rgb7.cmhy.repository

import com.trinity.android.rgb7.cmhy.model.Post
import com.trinity.android.rgb7.cmhy.api.RetrofitInstance
import com.trinity.android.rgb7.cmhy.model.PostPlaceList

import retrofit2.Response

class Repository {

    //Get  Place Detail
    suspend fun getPost2(number: Int): Response<Post>{
        return RetrofitInstance.api.getPost2(number)
    }

    //Get Post Place List
    suspend fun getCustomPosts(number:Int ,userId: Int, sort: String, order: String): Response<List<PostPlaceList>> {
        return RetrofitInstance.api.getCustomPosts(number ,userId, sort, order)
    }

    //Get Post Searching
    suspend fun getCustomPostsSearch(searching:String, userId: String, sort: String, order: String): Response<List<PostPlaceList>> {
        return RetrofitInstance.api.getCustomPostsSearch(searching ,userId, sort, order)
    }

    //Get Post Place Map List
    suspend fun getCustomPostsMapList(number:Int ,userId: Int, sort: String, order: String): Response<List<PostPlaceList>> {
        return RetrofitInstance.api.getCustomPostsMapList(number ,userId, sort, order)
    }

    //Get Post Searching Map List
    suspend fun getCustomPostsSearchMapList(searching:String, userId: String, sort: String, order: String): Response<List<PostPlaceList>> {
        return RetrofitInstance.api.getCustomPostsSearchMapList(searching ,userId, sort, order)
    }


}