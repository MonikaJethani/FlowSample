package com.example.flowsample.repository

import app.cash.turbine.test
import com.example.flowsample.CommentApiState
import com.example.flowsample.CoroutineTestRule
import com.example.flowsample.model.CommentModel
import com.example.flowsample.network.ApiService
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.spyk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
class CommentsRepositoryTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @MockK
    lateinit var apiService: ApiService

    lateinit var repo: CommentsRepository

    @Before
    fun setUp() {
        repo = spyk(CommentsRepository(mockk<ApiService>(), coroutinesTestRule.testDispatcher))
        mockkClass(ApiService::class)
    }


    @OptIn(ExperimentalTime::class)
    @Test
    fun testGetComments() {
        val mockResponse: CommentApiState<CommentModel> = CommentApiState.success(
            CommentModel(1, 1, "abc", "xyz")
        )
        coEvery { repo.getComment(any()) } returns flowOf(mockResponse)
        coroutinesTestRule.testDispatcher.runBlockingTest {
            //response.collect { assertNotNull(it) }
            val response: Flow<CommentApiState<CommentModel>> = repo.getComment(1)
            response.test {
                assertEquals(awaitItem(), mockResponse)
                awaitComplete()
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun testGetCommentsError() {
        // Mock
        val apiService = mockk<ApiService>()
        coEvery { apiService.getComments(1) } coAnswers {
            throw IOException()
        }
        repo = spyk(CommentsRepository(apiService, coroutinesTestRule.testDispatcher))
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val response: Flow<CommentApiState<CommentModel>> = repo.getComment(1)
            response.test {
                assertThat(
                    awaitError(),
                    instanceOf(IOException::class.java)
                )
            }
        }
    }
}