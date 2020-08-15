package dev.sarveshathawale.altimetrikcodingchallange.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import dev.sarveshathawale.altimetrikcodingchallange.R
import dev.sarveshathawale.altimetrikcodingchallange.extensions.visibleGone
import dev.sarveshathawale.altimetrikcodingchallange.network.response.Album
import kotlinx.android.synthetic.main.list_item_search_album.view.*
import java.util.*

@Suppress("UNCHECKED_CAST")
class AlbumAdapter(
    private val albums: ArrayList<Album>,
    private val onAddToCartClickListener: OnAddToCartClickListener
) : RecyclerView.Adapter<AlbumAdapter.AlbumRowViewHolder>(), Filterable {

    private var allAlbums: ArrayList<Album> = arrayListOf()

    class AlbumRowViewHolder(
        itemView: View,
        private val onAddToCartClickListener: OnAddToCartClickListener
    ) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(album: Album) {
            itemView.apply {
                tvTrackName.text = album.trackName
                tvArtistName.text = String.format(
                    tvArtistName.context.getString(R.string.artist_name_format),
                    album.artistName
                )
                tvCollectionPrice.text = String.format(
                    tvArtistName.context.getString(R.string.collection_price_format),
                    album.collectionPrice, album.currency
                )
                btnAddToCart.visibility = album.isAddedToCart.not().visibleGone()
                ivArtWork.load(album.artworkUrl100)

                btnAddToCart.setOnClickListener {
                    album.isAddedToCart = true
                    onAddToCartClickListener.onAddToCartClick(album)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumRowViewHolder {
        return AlbumRowViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_search_album, parent, false),
            onAddToCartClickListener
        )
    }

    override fun onBindViewHolder(holder: AlbumRowViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    override fun getItemCount(): Int = albums.size

    fun addAlbums(albums: List<Album>) {
        this.albums.apply {
            clear()
            addAll(albums)
        }
        this.allAlbums.apply {
            clear()
            addAll(albums)
        }
    }

    override fun getFilter(): Filter {
        return AlbumFilters()
    }

    inner class AlbumFilters : android.widget.Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = arrayListOf<Album>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(allAlbums)
            } else {
                val filteredPattern = constraint.toString().toLowerCase().trim()
                for (album in allAlbums) {
                    if (album.trackName.toLowerCase(Locale.getDefault()).trim()
                            .contains(filteredPattern)
                    ) {
                        filteredList.add(album)
                    }
                }
            }
            return FilterResults().apply {
                values = filteredList
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            albums.clear()
            albums.addAll(results?.values as List<Album>)
            notifyDataSetChanged()
        }
    }

    fun sortByCollectionName() {
        albums.sortBy { it.collectionName }
        notifyDataSetChanged()
    }

    fun sortByArtistName() {
        albums.sortBy { it.artistName }
        notifyDataSetChanged()
    }

    fun sortByTrackName() {
        albums.sortBy { it.trackName }
        notifyDataSetChanged()
    }

    interface OnAddToCartClickListener {
        fun onAddToCartClick(album: Album)
    }
}