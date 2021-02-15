package com.nikhil.tmdbtask.ui.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikhil.tmdbtask.data.remote.NetworkService
import java.lang.IllegalArgumentException

class MovieDetailsViewModelFactory(private val networkService: NetworkService):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if(modelClass.isAssignableFrom(MovieDetailsViewModel::class.java))
       {
           return MovieDetailsViewModel(networkService) as T
       }
       else{
           throw IllegalArgumentException("Unknown View Model")
       }
    }
}