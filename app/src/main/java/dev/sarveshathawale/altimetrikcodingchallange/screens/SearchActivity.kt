package dev.sarveshathawale.altimetrikcodingchallange.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.sarveshathawale.altimetrikcodingchallange.R
import dev.sarveshathawale.altimetrikcodingchallange.network.api.Resource
import dev.sarveshathawale.altimetrikcodingchallange.network.response.Album
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), SortBottomSheet.OnActionSelectionListener,
    AlbumAdapter.OnAddToCartClickListener {

    private lateinit var adapter: AlbumAdapter
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeUi()
        setupObservers()
    }

    private fun initializeUi() {
        adapter = AlbumAdapter(arrayListOf(), this)
        val dividerItemDecoration = DividerItemDecoration(
            rcvSearchAlbum.context,
            LinearLayoutManager.VERTICAL
        )
        ContextCompat.getDrawable(this, R.drawable.divider)
            ?.let { dividerItemDecoration.setDrawable(it) }
        rcvSearchAlbum.addItemDecoration(
            dividerItemDecoration
        )
        rcvSearchAlbum.adapter = adapter

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (::adapter.isInitialized) {
                    adapter.filter.filter(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        tvSort.setOnClickListener {
            SortBottomSheet().show(supportFragmentManager, "")
        }
    }

    private fun setupObservers() {
        viewModel.getAlbums().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        groupSuccessList.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { albumsResponse -> retrieveList(albumsResponse.albums) }
                        Timber.e("Success")
                    }
                    Resource.Status.ERROR -> {
                        groupSuccessList.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        Timber.e("Error: ${it.message}")
                    }
                    Resource.Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        groupSuccessList.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(album: List<Album>) {
        adapter.apply {
            addAlbums(album)
            notifyDataSetChanged()
        }
    }

    override fun onSortSelection(sortOptions: SortBottomSheet.SortOptions) {
        if (::adapter.isInitialized) {
            when (sortOptions) {
                SortBottomSheet.SortOptions.COLLECTION_NAME -> {
                    adapter.sortByCollectionName()
                }
                SortBottomSheet.SortOptions.TRACK_NAME -> {
                    adapter.sortByTrackName()
                }
                SortBottomSheet.SortOptions.ARTIST_NAME -> {
                    adapter.sortByArtistName()
                }
            }
        }
    }

    override fun onAddToCartClick(album: Album) {
        Toast.makeText(this, "Album Added to Cart Successfully!", Toast.LENGTH_SHORT).show()
        adapter.notifyDataSetChanged()
    }
}