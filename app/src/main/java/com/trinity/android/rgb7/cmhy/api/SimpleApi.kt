package com.trinity.android.rgb7.cmhy.api

import com.trinity.android.rgb7.cmhy.model.Post
import com.trinity.android.rgb7.cmhy.model.PostPlaceList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface SimpleApi {

    @Headers("Cache-Control: max-age=640000")


    //---- For Place Detail-----
    @GET("app/place/{postNumber}")
    suspend fun getPost2(
            @Path("postNumber") number: Int
    ): Response<Post>

    // --- For Place List ----
    @GET("app/places/{postNumber}")
    suspend fun getCustomPosts(
            @Path("postNumber") PlaceIdPath: Int,
            @Query("userId") userId: Int,
            @Query("_sort") sort: String,
            @Query("_order") order: String
    ): Response<List<PostPlaceList>>

    //-- For Searching ---& Franchise
    @GET("app/search/{searching}")
    suspend fun getCustomPostsSearch(
            @Path("searching") PlaceIdPath: String,
            @Query("franchise") franchise: String,
            @Query("_sort") sort: String,
            @Query("_order") order: String
    ): Response<List<PostPlaceList>>

    //Place List Map List
    @GET("app/places_map/{postNumber}")
    suspend fun getCustomPostsMapList(
            @Path("postNumber") PlaceIdPath: Int,
            @Query("userId") userId: Int,
            @Query("_sort") sort: String,
            @Query("_order") order: String
    ): Response<List<PostPlaceList>>


    //-- For Searching Map List  ---& Franchise Map List
    @GET("app/search_map/{searching}")
    suspend fun getCustomPostsSearchMapList(
            @Path("searching") PlaceIdPath: String,
            @Query("franchise") franchise: String,
            @Query("_sort") sort: String,
            @Query("_order") order: String
    ): Response<List<PostPlaceList>>



}