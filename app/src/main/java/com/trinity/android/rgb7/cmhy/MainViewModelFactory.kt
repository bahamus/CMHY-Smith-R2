package com.trinity.android.rgb7.cmhy


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trinity.android.rgb7.cmhy.repository.Repository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }

}