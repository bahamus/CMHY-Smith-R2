package com.trinity.android.rgb7.cmhy

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trinity.android.rgb7.cmhy.repository.Repository
import com.trinity.android.rgb7.cmhy.model.Post
import com.trinity.android.rgb7.cmhy.model.PostPlaceList
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {


    //Place Detail
    var myResponse2: MutableLiveData<Response<Post>> = MutableLiveData()
    //Place List
    var myCustomPosts: MutableLiveData<Response<List<PostPlaceList>>> = MutableLiveData()
    //Search
    var myCustomPostsSearch: MutableLiveData<Response<List<PostPlaceList>>> = MutableLiveData()



    //-----Place Detail --------------------
    fun getPost2(number: Int){
        viewModelScope.launch {
            val response = repository.getPost2(number)
            myResponse2.value = response
        }

    }

    //-----Place List --------------------
    fun getCustomPosts(PlaceIdPath:Int,userId: Int, sort: String, order: String) {
        viewModelScope.launch {
            val response = repository.getCustomPosts(PlaceIdPath,userId, sort, order)
            myCustomPosts.value = response
        }
    }

    //-----Search --------------------
    fun getCustomPostsSearch(PlaceIdPath:String,franchise: String, sort: String, order: String) {
        viewModelScope.launch {
            val response = repository.getCustomPostsSearch(PlaceIdPath,franchise, sort, order)
            myCustomPostsSearch.value = response

        }
    }

    //-----Map List --------------------
    fun getCustomPostsMapList(PlaceIdPath:Int,userId: Int, sort: String, order: String) {
        viewModelScope.launch {
            val response = repository.getCustomPostsMapList(PlaceIdPath,userId, sort, order)
            myCustomPosts.value = response
        }
    }

    //-----Search Map List --------------------
    fun getCustomPostsSearchMapList(PlaceIdPath:String,franchise: String, sort: String, order: String) {
        viewModelScope.launch {
            val response = repository.getCustomPostsSearchMapList(PlaceIdPath,franchise, sort, order)
            myCustomPostsSearch.value = response

        }
    }

}