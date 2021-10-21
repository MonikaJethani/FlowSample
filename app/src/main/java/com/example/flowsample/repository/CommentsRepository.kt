package com.example.flowsample.repository

import com.example.flowsample.CommentApiState
import com.example.flowsample.network.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CommentsRepository(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getComment(id: Int) = flow {

        // get the comment Data from the api
        val comment = apiService.getComments(id)

        // Emit this data wrapped in
        // the helper class [CommentApiState]
        emit(CommentApiState.success(comment))
    }.flowOn(dispatcher)

}