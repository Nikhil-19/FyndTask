package com.nikhil.tmdbtask.ui.movieDetails
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nikhil.tmdbtask.data.remote.NetworkService
import com.nikhil.tmdbtask.data.remote.repository.MovieRepository
import com.nikhil.tmdbtask.data.remote.response.MovieDetailsResponse
import com.redmango.couroutinespractise.apicoroutine.Resource
import kotlinx.coroutines.Dispatchers


class MovieDetailsViewModel(private val networkService: NetworkService) : ViewModel() {

    private val movieRepository = MovieRepository(networkService)


    fun fetchMovieDetails(apiKey:String,argMovieId:Int)= liveData<Resource<MovieDetailsResponse>>(Dispatchers.IO) {
        emit(Resource.loading(null,"Fetching Details..."))
        try {
            val movieDetailsResponse=movieRepository.fetchMovieDetails(apiKey,argMovieId)
                emit(Resource.success(movieDetailsResponse))

        } catch (e: Exception) {
            emit(Resource.error(null, e.localizedMessage))
        }
    }
}