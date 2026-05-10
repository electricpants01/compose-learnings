package com.example.testapp

import com.example.testapp.data.model.Post
import com.example.testapp.data.repository.PostRepository
import com.example.testapp.viewmodel.PostEvent
import com.example.testapp.viewmodel.PostSideEffect
import com.example.testapp.viewmodel.PostUiState
import com.example.testapp.viewmodel.PostViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class PostViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: PostRepository
    private lateinit var viewModel: PostViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = PostViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading and then loads posts`() = runTest {
        val posts = listOf(Post(1, 1, "Title 1", "Body 1"))
        coEvery { repository.getPosts() } returns Result.success(posts)

        viewModel.onEvent(PostEvent.LoadPosts)
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals(posts, uiState.posts)
        assertEquals(null, uiState.errorMessage)
    }

    @Test
    fun `loading posts shows error on failure`() = runTest {
        val errorMessage = "Network error"
        coEvery { repository.getPosts() } returns Result.failure(Exception(errorMessage))

        viewModel.onEvent(PostEvent.LoadPosts)
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals(errorMessage, uiState.errorMessage)
        assertTrue(uiState.posts.isEmpty())
    }

    @Test
    fun `load post detail updates selected post`() = runTest {
        val post = Post(1, 1, "Title 1", "Body 1")
        coEvery { repository.getPostById(1) } returns Result.success(post)

        viewModel.onEvent(PostEvent.LoadPostDetail(1))
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals(post, uiState.selectedPost)
        assertEquals(null, uiState.errorMessage)
    }

    @Test
    fun `load post detail shows error on failure`() = runTest {
        val errorMessage = "Detail error"
        coEvery { repository.getPostById(1) } returns Result.failure(Exception(errorMessage))

        viewModel.onEvent(PostEvent.LoadPostDetail(1))
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals(errorMessage, uiState.errorMessage)
        assertEquals(null, uiState.selectedPost)
    }

    @Test
    fun `onPostClicked emits NavigateToDetail side effect`() = runTest {
        val collectJob = launch(testDispatcher) {
            viewModel.sideEffect.collect { sideEffect ->
                assertIs<PostSideEffect.NavigateToDetail>(sideEffect)
                assertEquals(1, sideEffect.postId)
            }
        }

        viewModel.onEvent(PostEvent.OnPostClicked(1))
        collectJob.cancel()
    }

    @Test
    fun `onBackClicked emits NavigateBack side effect`() = runTest {
        val collectJob = launch(testDispatcher) {
            viewModel.sideEffect.collect { sideEffect ->
                assertIs<PostSideEffect.NavigateBack>(sideEffect)
            }
        }

        viewModel.onEvent(PostEvent.OnBackClicked)
        collectJob.cancel()
    }
}
