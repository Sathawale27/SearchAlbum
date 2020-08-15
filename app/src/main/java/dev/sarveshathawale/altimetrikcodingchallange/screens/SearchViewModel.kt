package dev.sarveshathawale.altimetrikcodingchallange.screens

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dev.sarveshathawale.altimetrikcodingchallange.network.api.AlbumApi
import dev.sarveshathawale.altimetrikcodingchallange.network.api.Resource
import dev.sarveshathawale.altimetrikcodingchallange.network.response.Album
import kotlinx.coroutines.Dispatchers

class SearchViewModel @ViewModelInject constructor(private val albumApi: AlbumApi) :
    ViewModel() {

    fun getAlbums() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = albumApi.getAlbums()))
        } catch (exception: Exception) {
            emit(
                Resource.error(
                    data = null,
                    message = exception.message ?: "Please try again later."
                )
            )
        }
    }
}