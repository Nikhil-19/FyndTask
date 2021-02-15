package com.nikhil.tmdbtask.ui.movieDetails

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.nikhil.motialoswaltask.utility.Constants
import com.nikhil.tmdbtask.R
import com.nikhil.tmdbtask.data.remote.response.MovieDetailsResponse
import com.nikhil.tmdbtask.databinding.ActivityMovieDetailsBinding
import com.redmango.couroutinespractise.apicoroutine.Status
import com.redmango.couroutinespractise.apicoroutine.data.remote.Networking

class MovieDetailsActivity : AppCompatActivity() {
    private var movieIdObtained: Int = 0
    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var viewModelFactory: MovieDetailsViewModelFactory
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        viewModelFactory = MovieDetailsViewModelFactory(Networking.create())
        movieDetailsViewModel =
            ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel::class.java)
        movieIdObtained = intent.getIntExtra(Constants.INTENT_KEY_MOVIE_ID, 0)

        setObservers()
    }

    private fun setObservers() {
        movieDetailsViewModel.fetchMovieDetails(getString(R.string.api_key),movieIdObtained).observe(this, {
            it?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                      Log.d("Movie Details",it.data?.originalTitle)
                      setMovieDetails(it?.data)
                    }
                    Status.LOADING -> {

                    }
                    Status.ERROR -> {
                        Log.d("Movie Details Error",it.message?:getString(R.string.error_msg))
                    }
                    else -> {
                        Log.d("Movie Details Error",getString(R.string.error_msg))
                    }
                }
            }

        })
    }

    private fun setMovieDetails(data: MovieDetailsResponse?) {

        data?.let {
            val imageUrl="https://image.tmdb.org/t/p/w500"+data.backdropPath
            binding.movieDetails=it
            binding.imageUrlPoster=imageUrl
            loadImage(binding.ivPoster,imageUrl)
        }

    }


    private fun loadImage(view: ImageView, imageUrl: String) {
        Glide.with(view.context)
            .load(imageUrl)
            .into(view)
    }


}