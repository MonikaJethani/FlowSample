package com.example.flowsample.viewmodel

import androidx.lifecycle.ViewModel
import com.example.flowsample.network.AppConfig
import com.example.flowsample.repository.CommentsRepository
import kotlinx.coroutines.Dispatchers

class CommentsViewModel : ViewModel() {

    // Create a Repository and pass the api
    // service we created in AppConfig file
    private val repository = CommentsRepository(
        AppConfig.ApiService(), Dispatchers.IO
    )

    init {
        // Initiate a starting
        // search with comment Id 1
        getNewComment(1)
    }

    fun getNewComment(i: Int) {

    }

}