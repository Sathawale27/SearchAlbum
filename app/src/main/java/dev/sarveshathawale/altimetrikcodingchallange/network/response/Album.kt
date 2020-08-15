package dev.sarveshathawale.altimetrikcodingchallange.network.response

data class Album(
    var artistName: String,
    var trackName: String,
    var collectionCensoredName: String,
    var trackCensoredName: String,
    var artworkUrl100: String,
    var collectionPrice: Float,
    var currency: String,
    var releaseDate: String,
    var collectionName: String,
    var isAddedToCart: Boolean = false
)