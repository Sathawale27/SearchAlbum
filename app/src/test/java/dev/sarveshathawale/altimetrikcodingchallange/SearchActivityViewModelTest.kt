package dev.sarveshathawale.altimetrikcodingchallange

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import dev.sarveshathawale.altimetrikcodingchallange.network.api.AlbumApi
import dev.sarveshathawale.altimetrikcodingchallange.network.api.Resource
import dev.sarveshathawale.altimetrikcodingchallange.network.response.Album
import dev.sarveshathawale.altimetrikcodingchallange.network.response.AlbumResponse
import dev.sarveshathawale.altimetrikcodingchallange.screens.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchActivityViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var albumApi: AlbumApi

    @Mock
    private lateinit var albumApiObserver: Observer<Resource<AlbumResponse>>

    @Before
    fun setUp() {
        // do something if required
    }

    @Test
    fun successWithData_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            val response = albumResponse()

            doReturn(Resource.success(data = response))
                .`when`(albumApi)
                .getAlbums()

            val viewModel = SearchViewModel(albumApi)
            viewModel.getAlbums().observeForever(albumApiObserver)

            verify(albumApi).getAlbums()
            verify(albumApiObserver).onChanged(Resource.success(response))

            viewModel.getAlbums().removeObserver(albumApiObserver)
        }
    }

    private fun albumResponse(): AlbumResponse {
        return AlbumResponse(
            1,
            arrayListOf(
                Album(
                    artistName = "Meghan Trainor",
                    trackName = "All About That Bass",
                    collectionCensoredName = "Title (Deluxe Edition)",
                    trackCensoredName = "All About That Bass",
                    artworkUrl100 = "https://is2-ssl.mzstatic.com/image/thumb/Music5/v4/41/7d/a2/417da2f7-b676-4dcb-8f41-8278a2501bf4/source/100x100bb.jpg",
                    collectionName = "Title (Deluxe Edition)",
                    collectionPrice = 12.99f,
                    currency = "USD",
                    releaseDate = "2014-06-30T07:00:00Z"
                )
            )
        )
    }

    @After
    fun tearDown() {
        // do something if required
    }
}