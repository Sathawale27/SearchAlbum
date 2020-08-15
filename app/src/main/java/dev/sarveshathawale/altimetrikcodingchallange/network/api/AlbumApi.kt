package dev.sarveshathawale.altimetrikcodingchallange.network.api

import dev.sarveshathawale.altimetrikcodingchallange.network.response.AlbumResponse
import retrofit2.http.GET

interface AlbumApi {

    @GET("750dc42e-ad94-41d4-9d3f-693f290450c2")
    suspend fun getAlbums(): AlbumResponse
}