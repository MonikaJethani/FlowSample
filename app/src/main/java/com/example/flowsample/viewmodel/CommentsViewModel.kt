package com.example.flowsample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowsample.CommentApiState
import com.example.flowsample.Status
import com.example.flowsample.model.CommentModel
import com.example.flowsample.network.AppConfig
import com.example.flowsample.repository.CommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class CommentsViewModel : ViewModel() {

    // Create a Repository and pass the api
    // service we created in AppConfig file
    private val repository = CommentsRepository(
        AppConfig.ApiService(), Dispatchers.IO
    )

    val commentState = MutableStateFlow(
        CommentApiState(
            Status.LOADING,
            CommentModel(), ""
        )
    )

    init {
        // Initiate a starting
        // search with comment Id 1
        getNewComment(1)
    }

    fun getNewComment(i: Int) {
// Since Network Calls takes time,Set the
        // initial value to loading state
        commentState.value = CommentApiState.loading()

        // ApiCalls takes some time, So it has to be
        // run and background thread. Using viewModelScope
        // to call the api
        viewModelScope.launch {

            // Collecting the data emitted
            // by the function in repository
            repository.getComment(i)
                // If any errors occurs like 404 not found
                // or invalid query, set the state to error
                // State to show some info
                // on screen
                .catch {
                    commentState.value =
                        CommentApiState.error(it.message.toString())
                }
                // If Api call is succeeded, set the State to Success
                // and set the response data to data received from api
                .collect {
                    commentState.value = CommentApiState.success(it.data)
                }
        }
    }

}