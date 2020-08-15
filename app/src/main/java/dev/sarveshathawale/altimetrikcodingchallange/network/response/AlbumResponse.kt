package dev.sarveshathawale.altimetrikcodingchallange.network.response

import com.google.gson.annotations.SerializedName

data class AlbumResponse(
    val resultCount: Int,
    @SerializedName("results")
    val albums: List<Album>
)