package com.example.flowsample.repository

import com.example.flowsample.network.ApiService
import kotlinx.coroutines.CoroutineDispatcher

class CommentsRepository(private val apiService: ApiService,
                         private val dispatcher: CoroutineDispatcher) {
}